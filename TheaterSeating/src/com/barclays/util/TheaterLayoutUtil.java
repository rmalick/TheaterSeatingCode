package com.barclays.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import com.barclays.exception.OrderFormatException;
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
					.println("*****************************************************");
			System.out.println("Layout");
			System.out
					.println("*****************************************************\n");
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
					.println("\n\n*****************************************************");
			System.out.println("Orders");
			System.out
					.println("*****************************************************\n");
			int orderInex = 1;
			while ((line = inputStream.readLine()) != null) {
				try {
					String[] str = line.split(" ");
					int orderSize = Integer.parseInt(str[1]);
					if (orderSize < 0)
						throw new OrderFormatException();
					Order order = new Order(orderInex, str[0], orderSize);
					orderMap.put(orderInex++, order);
					System.out.println(order);
				} catch (NumberFormatException exception) {
					throw new OrderFormatException();
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return layout;
	}

}
