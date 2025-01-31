package com.spoteditor.backend.place.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

	private String address;
	private String roadAddress;
	private double latitude;
	private double longitude;
	private String sido;
	private String bname;
	private String sigungu;

	@Builder
	public Address(String address, String roadAddress, double latitude,
				   double longitude, String sido, String bname, String sigungu) {
		this.address = address;
		this.roadAddress = roadAddress;
		this.latitude = latitude;
		this.longitude = longitude;
		this.sido = sido;
		this.bname = bname;
		this.sigungu = sigungu;
	}
}
