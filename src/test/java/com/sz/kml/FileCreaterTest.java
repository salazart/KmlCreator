package com.sz.kml;

import java.io.File;
import java.io.FileNotFoundException;

import com.sz.kml.services.CoordinateService;
import com.sz.kml.services.KmlService;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Kml;

public class FileCreaterTest {
	private static final int TIMING = 1;
	private static final int AZIMUTH = 360;
	private static final String MARK_NAME = "testMark";
	private static final String FILE_NAME = "test.kml";
	
	private static Coordinate coordinate = new Coordinate(26.996139215314, 49.4340943889666);
	
	private static ICoordinate coordinateImpl = new CoordinateService();
	private static KmlService kmlService = new KmlService(coordinateImpl);
	
	public static void main(String[] args) {
		kmlService.addKmlMark(coordinate, MARK_NAME);
		kmlService.addKmlMarkSector(MARK_NAME, coordinate, TIMING, AZIMUTH);
		Kml kml = kmlService.getKml();
		
		try {
			kml.marshal(new File(FILE_NAME));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
