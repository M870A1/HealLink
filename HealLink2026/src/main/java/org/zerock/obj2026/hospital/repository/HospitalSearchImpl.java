package org.zerock.obj2026.hospital.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.obj2026.hospital.domain.Hospital;
import org.zerock.obj2026.hospital.domain.QHospital;

import java.util.List;

public class HospitalSearchImpl extends QuerydslRepositorySupport implements HospitalSearch {

    public HospitalSearchImpl() {
        super(Hospital.class);
    }

    @Override
    public Page<Hospital> search(String[] types, String keyword, Pageable pageable) {
        QHospital hospital = QHospital.hospital;
        JPQLQuery<Hospital> query = from(hospital);

        if ((types != null && types.length > 0) && keyword != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for (String type : types) {
                switch (type) {
                    case "n":  // 병원이름 name n
                        booleanBuilder.or(hospital.dutyName.contains(keyword));
                        // 병원 이름(name)에 keyword가 포함되어있으면, OR 조건으로 추가
                        break;
                    case "a": // 주소 address a
                        booleanBuilder.or(hospital.dutyAddr.contains(keyword));
                        // 병원 주소(address)에 keyword가 포함되어있으면, OR 조건으로 추가
                        break;
                }
            }
            query.where(booleanBuilder);
        }

        query.where(hospital.hpid.gt("0"));

        //페이징
        this.getQuerydsl().applyPagination(pageable, query);

        List<Hospital> list = query.fetch();
        long total = query.fetchCount();

        return new PageImpl<>(list, pageable, total);
    }
}
