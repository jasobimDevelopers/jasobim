package base.util;

/**
 * 经纬度对象
 * @author zj
 *
 */
public class MapLocationEntity {
	private Double lon;
	private Double lat;
	
	/**
	 * 经纬度对象
	 * @param lon
	 * @param lat
	 */
	public MapLocationEntity(Double lon, Double lat) {
		super();
		this.lon = lon;
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}
	
	
}
