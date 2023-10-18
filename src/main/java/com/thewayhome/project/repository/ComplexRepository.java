package com.thewayhome.project.repository;

import com.thewayhome.project.domain.Complex;
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

public interface ComplexRepository extends JpaRepository<Complex, Long> {

    @Query(value = "SELECT * FROM complex AS c WHERE MBRCONTAINS(ST_LINESTRINGFROMTEXT(CONCAT('LINESTRING(', :ne_lat, ' ', :ne_lng, ', ', :sw_lat, ' ', :sw_lng, ')')), c.location)", nativeQuery = true)
    List<Complex> findComplexesInBoundingBox(
            @Param("sw_lng") double swLng,
            @Param("sw_lat") double swLat,
            @Param("ne_lng") double neLng,
            @Param("ne_lat") double neLat
    );

    @Override
    void flush();

    @Override
    <S extends Complex> S saveAndFlush(S entity);

    @Override
    <S extends Complex> List<S> saveAllAndFlush(Iterable<S> entities);

    @Override
    void deleteAllInBatch(Iterable<Complex> entities);

    @Override
    void deleteAllByIdInBatch(Iterable<Long> longs);

    @Override
    void deleteAllInBatch();

    @Override
    Complex getOne(Long aLong);

    @Override
    Complex getById(Long aLong);

    @Override
    Complex getReferenceById(Long aLong);

    @Override
    <S extends Complex> List<S> findAll(Example<S> example);

    @Override
    <S extends Complex> List<S> findAll(Example<S> example, Sort sort);

    @Override
    <S extends Complex> List<S> saveAll(Iterable<S> entities);

    @Override
    List<Complex> findAll();

    @Override
    List<Complex> findAllById(Iterable<Long> longs);

    @Override
    <S extends Complex> S save(S entity);

    @Override
    Optional<Complex> findById(Long aLong);

    @Override
    boolean existsById(Long aLong);

    @Override
    long count();

    @Override
    void deleteById(Long aLong);

    @Override
    void delete(Complex entity);

    @Override
    void deleteAllById(Iterable<? extends Long> longs);

    @Override
    void deleteAll(Iterable<? extends Complex> entities);

    @Override
    void deleteAll();

    @Override
    List<Complex> findAll(Sort sort);

    @Override
    Page<Complex> findAll(Pageable pageable);

    @Override
    <S extends Complex> Optional<S> findOne(Example<S> example);

    @Override
    <S extends Complex> Page<S> findAll(Example<S> example, Pageable pageable);

    @Override
    <S extends Complex> long count(Example<S> example);

    @Override
    <S extends Complex> boolean exists(Example<S> example);

    @Override
    <S extends Complex, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);
}