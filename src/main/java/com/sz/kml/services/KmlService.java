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
	private Document document;
	private ICoordinate coordinateImpl;
	private Kml kml;

	public KmlService(ICoordinate coordinateImpl) {
		super();
		this.coordinateImpl = coordinateImpl;
		this.kml = new Kml();
		this.document = kml.createAndSetDocument();
		
	}
	
	public void addKmlMark(Coordinate coordinate, String markName){
		Placemark placemark = document.createAndAddPlacemark();
		placemark.withName(markName)
				.withOpen(Boolean.TRUE).createAndSetPoint()
				.addToCoordinates(coordinate.getLongitude(),coordinate.getLatitude());
	}
	
	public void addKmlMarkSector(String sectorName, Coordinate coordinate, int timing, int azimuth){
		
		Polygon polygon = document.createAndAddPlacemark().withName(sectorName).createAndSetPolygon();
		LinearRing linearRing = polygon.createAndSetOuterBoundaryIs().createAndSetLinearRing();
		
		linearRing.addToCoordinates(coordinate.getLongitude(),coordinate.getLatitude());
		List<Coordinate> coordinates = coordinateImpl.getCoordinates(coordinate, timing, azimuth);
		for (Coordinate c : coordinates) {
			linearRing.addToCoordinates(c.getLongitude(), c.getLatitude());
		}
		linearRing.addToCoordinates(coordinate.getLongitude(),coordinate.getLatitude());
	}
	
	public Kml getKml(){
		return this.kml;
	}
}
