package com.thewayhome.project.repository;

import com.thewayhome.project.domain.Complex;
import com.thewayhome.project.domain.RealComplex;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface RealComplexRepository extends JpaRepository<RealComplex, Long> {

    @Query(value = "SELECT * FROM real_complex AS c WHERE MBRCONTAINS(ST_LINESTRINGFROMTEXT(CONCAT('LINESTRING(', :ne_lat, ' ', :ne_lng, ', ', :sw_lat, ' ', :sw_lng, ')')), c.location)", nativeQuery = true)
    List<RealComplex> findComplexesInBoundingBox(
            @Param("sw_lng") double swLng,
            @Param("sw_lat") double swLat,
            @Param("ne_lng") double neLng,
            @Param("ne_lat") double neLat
    );

    @Override
    void flush();

    @Override
    <S extends RealComplex> S saveAndFlush(S entity);

    @Override
    <S extends RealComplex> List<S> saveAllAndFlush(Iterable<S> entities);

    @Override
    void deleteAllInBatch(Iterable<RealComplex> entities);

    @Override
    void deleteAllByIdInBatch(Iterable<Long> longs);

    @Override
    void deleteAllInBatch();

    @Override
    RealComplex getOne(Long aLong);

    @Override
    RealComplex getById(Long aLong);

    @Override
    RealComplex getReferenceById(Long aLong);

    @Override
    <S extends RealComplex> Optional<S> findOne(Example<S> example);

    @Override
    <S extends RealComplex> List<S> findAll(Example<S> example);

    @Override
    <S extends RealComplex> List<S> findAll(Example<S> example, Sort sort);

    @Override
    <S extends RealComplex> Page<S> findAll(Example<S> example, Pageable pageable);

    @Override
    <S extends RealComplex> long count(Example<S> example);

    @Override
    <S extends RealComplex> boolean exists(Example<S> example);

    @Override
    <S extends RealComplex, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);

    @Override
    <S extends RealComplex> S save(S entity);

    @Override
    <S extends RealComplex> List<S> saveAll(Iterable<S> entities);

    @Override
    Optional<RealComplex> findById(Long aLong);

    @Override
    boolean existsById(Long aLong);

    @Override
    List<RealComplex> findAll();

    @Override
    List<RealComplex> findAllById(Iterable<Long> longs);

    @Override
    long count();

    @Override
    void deleteById(Long aLong);

    @Override
    void delete(RealComplex entity);

    @Override
    void deleteAllById(Iterable<? extends Long> longs);

    @Override
    void deleteAll(Iterable<? extends RealComplex> entities);

    @Override
    void deleteAll();

    @Override
    List<RealComplex> findAll(Sort sort);

    @Override
    Page<RealComplex> findAll(Pageable pageable);
}