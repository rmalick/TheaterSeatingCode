package com.barclays.seat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import com.barclays.model.Order;
import com.barclays.model.Response;
import com.barclays.model.ResponseEnum;
import com.barclays.model.RowLayout;
import com.barclays.model.Section;
import com.barclays.model.TheaterLayout;

/**
 * 
 * @author ranjit_malick
 * 
 */
public class SeatAssignmentImpl implements SeatAssignment {

	private TheaterLayout theaterLayout;
	private Map<Integer, Response> responseMap = new TreeMap<Integer, Response>();

	public SeatAssignmentImpl(TheaterLayout theaterLayout) {
		this.theaterLayout = theaterLayout;

	}

	@Override
	public void generateResponse() {

		this.regenerateResponse();

		System.out
				.println("\n\nResponses\n-----------------------------------------");
		for (Integer index : responseMap.keySet()) {
			System.out.println(responseMap.get(index));
		}

	}

	/**
	 * Re assign if any possibility there to upgrade
	 */

	private void regenerateResponse() {

		RowLayout rowLayout;
		int rowSize = this.theaterLayout.getRowLayoutMap().size();
		for (int rowId = 1; rowId <= rowSize; rowId++) {
			rowLayout = this.theaterLayout.getRowLayoutMap().get(rowId);
			int sectionSize = rowLayout.getSectionList().size();
			for (int sectionId = 0; sectionId < sectionSize; sectionId++) {
				Section section = rowLayout.getSectionList().get(sectionId);
				int remainingSize = section.getRemainingSize();
				if (remainingSize > 0) {
					Response bestResponseToUpgrade = findBestUpgradableResponse(
							remainingSize, rowId);
					if (bestResponseToUpgrade != null) {

						Response newResponse = new Response(
								bestResponseToUpgrade.getOrder(), section,
								rowId);
						responseMap.put(bestResponseToUpgrade.getOrder()
								.getIndex(), newResponse);
						section.setRemainingSize(remainingSize
								- bestResponseToUpgrade.getOrder()
										.getRequiredSeatCount());

					}
				}

			}
		}

	}

	/**
	 * 
	 * @param remainingSize
	 * @param rowNumber
	 * @return Best Response which can be upgrade
	 */

	private Response findBestUpgradableResponse(int remainingSize, int rowNumber) {
		Response bestResponse = null;
		int unusedSeatCount = Integer.MAX_VALUE;
		for (Integer responseIndex : responseMap.keySet()) {
			Response response = responseMap.get(responseIndex);
			Integer requiredSeatCount = response.getOrder()
					.getRequiredSeatCount();
			if (response.getRowIndex() == null
					|| response.getRowIndex() <= rowNumber
					|| requiredSeatCount > remainingSize)
				continue;
			if (remainingSize - requiredSeatCount < unusedSeatCount) {
				unusedSeatCount = remainingSize - requiredSeatCount;
				bestResponse = response;
			}
		}

		if (bestResponse != null) {
			bestResponse.getSection().setRemainingSize(
					bestResponse.getSection().getRemainingSize()
							+ bestResponse.getOrder().getRequiredSeatCount());
		}

		return bestResponse;
	}

	/**
	 * 
	 * @param rowId
	 * @param sectionId
	 *            index of section in section List(so add 1)
	 * @param section
	 * @param orders
	 *            orders to book
	 */
	public void bookTicket(int rowId, int sectionId, Section section,
			Set<Order> orders) {
		for (Order order : orders) {
			int toltalRemainingSeats = this.theaterLayout
					.getToltalRemainingSeats();

			/*
			 * responseMap.put(order.getIndex(), new Response(order,
			 * order.getPartyName(), rowId, sectionId + 1));
			 */

			responseMap.put(order.getIndex(), new Response(order, section,
					rowId));

			theaterLayout.getOrderMap().remove(order.getIndex());
			section.setRemainingSize(section.getRemainingSize()
					- order.getRequiredSeatCount());

			this.theaterLayout.setToltalRemainingSeats(toltalRemainingSeats
					- order.getRequiredSeatCount());
		}
	}

	@Override
	public void startAssignment() {
		RowLayout rowLayout;
		int rowSize = this.theaterLayout.getRowLayoutMap().size();
		for (int rowId = 1; rowId <= rowSize; rowId++) {
			rowLayout = this.theaterLayout.getRowLayoutMap().get(rowId);
			int sectionSize = rowLayout.getSectionList().size();
			for (int sectionId = 0; sectionId < sectionSize; sectionId++) {
				Section section = rowLayout.getSectionList().get(sectionId);

				Set<Order> probableOrders = this.getProbableOrders(section);
				// System.out.println("Probable orders " + probableOrders);

				List<Set<Order>> eligibleOrders = this
						.getEligibleOrders_nextGen(probableOrders, section);

				TreeMap<Integer, Set<Order>> eligibleProrityOrderMap = this
						.arrangePriorityOrderMap(section, eligibleOrders);

				Set<Order> orderSet = this.findMostEligibleOrderSet(
						eligibleProrityOrderMap, rowId, sectionId,
						section.getRemainingSize());

				if (orderSet == null || orderSet.isEmpty()) {
					continue;
				} else {
					this.bookTicket(rowId, sectionId, section, orderSet);
				}
			}
		}
		if (this.theaterLayout.getOrderMap().size() > 0) {
			assignRemainingOrders();
		}
	}

	/**
	 * this method assign remaining orders
	 */

	private void assignRemainingOrders() {
		Set<Integer> orderKeys = this.theaterLayout.getOrderMap().keySet();
		SortedSet<Order> orders = new TreeSet<>(OrderReqSeatCmp.DESC_ORDER);
		for (Integer key : orderKeys) {
			orders.add(this.theaterLayout.getOrderMap().get(key));
		}
		// Collections.sort(orders, OrderReqSeatCmp.DESC_ORDER);
		for (Order order : orders) {
			if (order.getRequiredSeatCount() > this.theaterLayout
					.getToltalRemainingSeats()) {
				responseMap.put(order.getIndex(), new Response(order,
						ResponseEnum.CANT_ASSIGN));
				theaterLayout.getOrderMap().remove(order.getIndex());
			} else if (this.bookFirstEligibleSection(order) == null) {
				responseMap.put(order.getIndex(), new Response(order,
						ResponseEnum.SPLIT_TO_CALL));
				theaterLayout.getOrderMap().remove(order.getIndex());
			}
		}
	}

	private Section bookFirstEligibleSection(Order order) {
		Section sectionToBook = null;
		RowLayout rowLayout;
		int rowSize = this.theaterLayout.getRowLayoutMap().size();
		for (int rowId = 1; rowId <= rowSize; rowId++) {

			if (sectionToBook != null) {
				break;
			}

			rowLayout = this.theaterLayout.getRowLayoutMap().get(rowId);
			int sectionSize = rowLayout.getSectionList().size();
			for (int sectionId = 0; sectionId < sectionSize; sectionId++) {
				Section section = rowLayout.getSectionList().get(sectionId);
				if (section.getRemainingSize() >= order.getRequiredSeatCount()) {
					sectionToBook = section;
					this.bookTicket(rowId, sectionId, sectionToBook,
							new HashSet<Order>(Arrays.asList(order)));
					break;
				}
			}
		}
		return sectionToBook;
	}

	private Set<Order> findMostEligibleOrderSet(
			TreeMap<Integer, Set<Order>> eligibleProrityOrderMap, int rowId,
			int sectionId, int reqSeats) {
		Set<Order> orderSet = null;
		if (eligibleProrityOrderMap == null
				|| eligibleProrityOrderMap.isEmpty()) {
			return null;
		}
		Set<Order> firstOrder = eligibleProrityOrderMap.firstEntry().getValue();
		if (firstOrder.size() == 1)
		/* || eligibleProrityOrderMap.keySet().size() == 1 */{
			orderSet = firstOrder;
		} else {

			int sectionsAvlForExactMatch = countOtherSectionAvailableForBooking(
					rowId, sectionId, reqSeats);
			int totalExactMatch = 0;
			Set<Order> firstOrderExactMatch = null;
			for (Integer key : eligibleProrityOrderMap.keySet()) {
				Set<Order> orderSetCheck = eligibleProrityOrderMap.get(key);
				if (orderSetCheck.size() == 1) { // exact match case
					if (totalExactMatch == 0) {
						firstOrderExactMatch = orderSetCheck;
					}
					totalExactMatch++;
				}
			}
			if (totalExactMatch > sectionsAvlForExactMatch) {
				orderSet = firstOrderExactMatch;
			} else {
				orderSet = firstOrder;
			}
		}
		return orderSet;
	}

	private int countOtherSectionAvailableForBooking(int barredRowId,
			int barredSectionId, int reqSeats) {
		int result = 0;
		RowLayout rowLayout;
		int rowSize = this.theaterLayout.getRowLayoutMap().size();
		for (int rowId = 1; rowId <= rowSize; rowId++) {
			rowLayout = this.theaterLayout.getRowLayoutMap().get(rowId);
			int sectionSize = rowLayout.getSectionList().size();
			for (int sectionId = 0; sectionId < sectionSize; sectionId++) {
				if (barredRowId != rowId || barredSectionId != sectionId) {
					if (rowLayout.getSectionList().get(sectionId)
							.getRemainingSize() == reqSeats) {
						result++;
					}
				}
			}
		}

		return result;
	}

	/** This method sorts the map solely based on order placing */
	private TreeMap<Integer, Set<Order>> arrangePriorityOrderMap(
			Section section, List<Set<Order>> eligibleOrders) {
		TreeMap<Integer, Set<Order>> priorityOrderSetMap = new TreeMap<Integer, Set<Order>>();
		for (Set<Order> orderSet : eligibleOrders) {
			priorityOrderSetMap.put(this.getOrderPriorityIndex(orderSet),
					orderSet);
		}
		return priorityOrderSetMap;
	}

	/**
	 * 
	 * @param section
	 *            -considerable section
	 * @return Set of possible orders which can be fitted in this section
	 */

	private Set<Order> getProbableOrders(Section section) {
		SortedSet<Order> orders = new TreeSet<Order>(OrderReqSeatCmp.ASC_ORDER);
		Order order;
		for (Integer orderId : this.theaterLayout.getOrderMap().keySet()) {
			order = this.theaterLayout.getOrderMap().get(orderId);
			if (order.getRequiredSeatCount() <= section.getRemainingSize()) {
				orders.add(order);
			}
		}
		return orders;
	}

	private List<Set<Order>> getEligibleOrders_nextGen(
			Set<Order> probableOrders, Section section) {
		List<Set<Order>> orderSets = new ArrayList<Set<Order>>();

		List<Order> probableOrdersList = new ArrayList<Order>(probableOrders);

		for (int index1 = 0; index1 < probableOrdersList.size(); index1++) {
			Order startOrder = probableOrdersList.get(index1);
			int tempSize = startOrder.getRequiredSeatCount();
			int index3 = index1 + 1;
			Set<Order> orderSet = new HashSet<Order>();
			orderSet.add(startOrder);
			if (tempSize == section.getRemainingSize()) {
				orderSets.add(orderSet);
				break;
			}
			for (int index2 = index3; index2 < probableOrdersList.size(); index2++) {
				tempSize += probableOrdersList.get(index2)
						.getRequiredSeatCount();
				if (tempSize < section.getRemainingSize()) {
					orderSet.add(probableOrdersList.get(index2));
				} else if (tempSize == section.getRemainingSize()) {
					orderSet.add(probableOrdersList.get(index2));
					orderSets.add(orderSet);
					break;
				} else {
					orderSet.clear();
					orderSet.add(startOrder);
					tempSize = startOrder.getRequiredSeatCount();
					index2 = index3;
					index3++;
				}
			}
		}

		return orderSets;
	}

	/**
	 * 
	 * @param orders
	 * @return order index of first order in that set
	 */

	private int getOrderPriorityIndex(Set<Order> orders) {
		int priorityIndx = 0;
		for (Order order : orders) {
			if (priorityIndx == 0) {
				priorityIndx = order.getIndex();
			} else if (priorityIndx > order.getIndex()) {
				priorityIndx = order.getIndex();
			}
		}
		return priorityIndx;
	}

}
