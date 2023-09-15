package com.thewayhome.project.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.thewayhome.project.domain.Company;
import com.thewayhome.project.domain.QCompany;
import com.thewayhome.project.dto.company.CompanyGetFullNameResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CompanyRepositoryCustomImpl implements CompanyRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<CompanyGetFullNameResponseDto> searchCompanyByPartName(Pageable page, String part){
        QCompany qCompany = QCompany.company;

        JPAQuery<CompanyGetFullNameResponseDto> query =  jpaQueryFactory.select(Projections.constructor(CompanyGetFullNameResponseDto.class,
                qCompany.companyName.as("companyName"),
                qCompany.companyId.as("companyId")))
                .from(qCompany)
                .where(
                        qCompany.companyName.likeIgnoreCase("%" + part + "%")
                )
                .orderBy(qCompany.companyName.asc())
                ;

        long totalCount = query.fetchCount();
        List<CompanyGetFullNameResponseDto> companies = getSortedCompanies(query, page);
        return new PageImpl<>(companies, page, totalCount);
    }

    private static List<CompanyGetFullNameResponseDto> getSortedCompanies(JPAQuery<CompanyGetFullNameResponseDto> query, Pageable pageable){
        if (pageable.isUnpaged()) {
            return query.fetch();
        }

        query.offset(pageable.getOffset());
        query.limit(pageable.getPageSize());

        Sort sort = pageable.getSort();
        if (sort.isUnsorted()) {
            return query.fetch();
        }

        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        for (Sort.Order o : sort) {
            PathBuilder<CompanyGetFullNameResponseDto> entityPath = new PathBuilder<>(CompanyGetFullNameResponseDto.class, "companyGetFullNameResponseDto");
            Path<?> property = entityPath.get(o.getProperty());
            if (o.isAscending()) {
                orderSpecifiers.add(new OrderSpecifier(Order.ASC, property));
            } else {
                orderSpecifiers.add(new OrderSpecifier(Order.DESC, property));
            }
        }
        query.orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]));

        return query.fetch();

    }
}
