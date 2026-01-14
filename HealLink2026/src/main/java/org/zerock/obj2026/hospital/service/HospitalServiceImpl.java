package org.zerock.obj2026.hospital.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.obj2026.hospital.domain.Hospital;
import org.zerock.obj2026.hospital.dto.HPageRequestDTO;
import org.zerock.obj2026.hospital.dto.HpageResponseDTO;
import org.zerock.obj2026.hospital.dto.HospitalDTO;
import org.zerock.obj2026.hospital.repository.HospitalRepository;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {
    private final HospitalRepository hospitalRepository;
    private final ModelMapper modelMapper;


    @Override
    public HpageResponseDTO<HospitalDTO> list(HPageRequestDTO requestDTO) {
        String[] types = requestDTO.splitTypes();
        String keyword = requestDTO.getKeyword();
        Pageable pageable = requestDTO.getPageable();

        Page<Hospital> result = hospitalRepository.search(types, keyword, pageable);

        Page<HospitalDTO> dtoPage = result.map(hospital -> modelMapper.map(hospital, HospitalDTO.class));

        return new HpageResponseDTO<>(requestDTO, dtoPage);
    }

    @Override
    public HospitalDTO getHospitalById(String hospitalId) {
        Optional<Hospital> result = hospitalRepository.findById(hospitalId);
        if (result.isPresent()) {
            Hospital hospital = result.get();
            return modelMapper.map(hospital, HospitalDTO.class);
        }
        return null;
    }
}
