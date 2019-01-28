package si.recek.wealthbuild.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JsonMapper {

    ModelMapper modelMapper;

    @PostConstruct
    public void initialize() {
        modelMapper = new ModelMapper();

    }

    public <D> D map(Object source, Class<D> destinationType){
        return modelMapper.map(source, destinationType);
    }

    public <D> List<D> map(List list, Class<D> destinationType){
        list.stream().map(o -> modelMapper.map(o, destinationType)).collect(Collectors.toList());
        List<D> result = new ArrayList<>();
        for (Object o: list) {
            result.add(modelMapper.map(o, destinationType));
        }
        return result;
    }



}
