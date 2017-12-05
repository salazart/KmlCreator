package com.sz.kml;

import java.util.List;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;

public interface ICoordinate {
	List<Coordinate> getCoordinates(Coordinate coordinate, int timing, int azimuth);
}
