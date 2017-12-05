package com.sz.kml.services;

import java.util.ArrayList;
import java.util.List;

import com.sz.kml.ICoordinate;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;

public class CoordinateService implements ICoordinate{
	private static final int BEAM = 120;
	private List<Coordinate> coordinates = new ArrayList<>();
	private static final double equator = (40075696/1.508750754)*1.0083321403006442;
	
	public List<Coordinate> getCoordinates(Coordinate coordinate, int timing, int azimuth) {
		double r = timing * 550;
		double r1 = r + 550/2;
		double g1 = degreeToRad(azimuth + BEAM / 2);
		double g2 = degreeToRad(azimuth - BEAM / 2);
		
		double step = degreeToRad(BEAM) / 20;
		
		Coordinate stepCoordinate = metersToLatLonMove(coordinate, r1 * Math.sin(g1), r1 * Math.cos(g1));
		coordinates.add(stepCoordinate);
		
		while(true){
			g1 = g1 - step;
			if(g1 < g2) 
				break;
			stepCoordinate = metersToLatLonMove(coordinate, r1 * Math.sin(g1), r1 * Math.cos(g1));
			coordinates.add(stepCoordinate);
		}
		
		stepCoordinate = metersToLatLonMove(coordinate, r1 * Math.sin(g2), r1 * Math.cos(g2));
		coordinates.add(stepCoordinate);
		
		return coordinates;
	}

	private Coordinate metersToLatLonMove(Coordinate coordinate, double x, double y) {
		double lon = lonToMeters(coordinate.getLongitude()) + x;
		double lat = latToMeters(coordinate.getLatitude()) - y;
		return new Coordinate(metersToLon(lon), metersToLat(lat));
	}

	private double metersToLat(double lat) {
		double k = (lat - equator / 2) / (-equator / (2 * Math.PI));
		return (2 * Math.atan(Math.exp(k)) - Math.PI / 2) * 180 / Math.PI;
	}

	private double metersToLon(double lon) {
		return (lon - equator / 2) / (equator / 360);
	}

	private double latToMeters(double latitude) {
		double k = Math.sin(latitude * Math.PI / 180);
		return equator / 2 - 0.5 * Math.log((1 + k) / (1 - k)) * equator / (2 * Math.PI);
	}

	private double lonToMeters(double longitude) {
		return (equator / 2 + longitude * (equator / 360));
	}

	private double degreeToRad(double g) {
		return (Math.PI / 180) * g;
	}

}
