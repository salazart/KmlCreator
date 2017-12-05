package com.sz.kml.services;

import java.util.List;

import com.sz.kml.ICoordinate;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.LinearRing;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Polygon;

public class KmlService {
	private String name;
	private Document document;
	private ICoordinate coordinateImpl;

	public KmlService(String name, ICoordinate coordinateImpl) {
		super();
		this.name = name;
		this.coordinateImpl = coordinateImpl;
	}
	
	public Kml createKmlMark(Coordinate coordinate){
		final Kml kml = new Kml();
		document = kml.createAndSetDocument();
		
		Placemark placemark = document.createAndAddPlacemark();
		placemark.withName(name)
				.withOpen(Boolean.TRUE).createAndSetPoint()
				.addToCoordinates(coordinate.getLongitude(),coordinate.getLatitude());
		return kml;
	}
	
	public Kml createKmlMarkSector(Coordinate coordinate, int timing, int azimuth){
		Kml kml = createKmlMark(coordinate);
		
		Polygon polygon = document.createAndAddPlacemark().createAndSetPolygon();
		LinearRing linearRing = polygon.createAndSetOuterBoundaryIs().createAndSetLinearRing();
		
		linearRing.addToCoordinates(coordinate.getLongitude(),coordinate.getLatitude());
		List<Coordinate> coordinates = coordinateImpl.getCoordinates(coordinate, timing, azimuth);
		for (Coordinate c : coordinates) {
			linearRing.addToCoordinates(c.getLongitude(), c.getLatitude());
		}
		linearRing.addToCoordinates(coordinate.getLongitude(),coordinate.getLatitude());
		return kml;
	}
}
