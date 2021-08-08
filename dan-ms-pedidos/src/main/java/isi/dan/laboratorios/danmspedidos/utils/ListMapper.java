package isi.dan.laboratorios.danmspedidos.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListMapper {

    ModelMapper modelMapper;

    @Autowired
    ListMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <T, S> List<S> mapList(List<T> source, Class<S> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
}