package com.rodrigo.os.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

public class Mapper {
	
	private static ModelMapper mapper = new ModelMapper();
	
	public static <O, D> D parseObject(O origin, Class<D> destination) {
		return mapper.map(origin, destination);
	}
	
	public static <O, D> List<D> parseListObjects(List <O> origin, Class<D> destination) {
		List<D> destinationObject = new ArrayList<>();
		for (O o : origin) {
			destinationObject.add(mapper.map(o, destination));
		}
		return destinationObject;
	}
}
