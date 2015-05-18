package com.barclays.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import com.barclays.model.Order;
import com.barclays.model.RowLayout;
import com.barclays.model.TheaterLayout;

public class TheaterLayoutUtil {

	public static TheaterLayout createLayout(File layoutFile) {

		TheaterLayout layout = new TheaterLayout();
		Map<Integer, RowLayout> rowLayoutMap = layout.getRowLayoutMap();
		Map<Integer, Order> orderMap = layout.getOrderMap();

		try (BufferedReader inputStream = new BufferedReader(new FileReader(
				layoutFile))) {
			String line;

			// parse seat layout
			System.out
					.println("Layout\n-----------------------------------------------------");
			int rowIndex = 1;
			while ((line = inputStream.readLine()) != null) {
				if (line.trim().isEmpty()) {
					break;
				}
				String[] sections = line.split(" ");
				RowLayout rowLayout = new RowLayout(rowIndex, sections);
				layout.setToltalRemainingSeats(layout.getToltalRemainingSeats()
						+ rowLayout.getTotalSeat());
				if (rowLayout.getMaxSectionSize() > layout.getMaxSectionSize())
					layout.setMaxSectionSize(rowLayout.getMaxSectionSize());
				rowLayoutMap.put(rowIndex++, rowLayout);
				System.out.println(rowLayout);
			}

			// parse order layout
			System.out
					.println("\nOrders\n-----------------------------------------------------");
			int orderInex = 1;
			while ((line = inputStream.readLine()) != null) {

				String[] str = line.split(" ");
				Order order = new Order(orderInex, str[0],
						Integer.parseInt(str[1]));
				orderMap.put(orderInex++, order);
				System.out.println(order);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return layout;
	}

}
