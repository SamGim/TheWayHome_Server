package com.thewayhome.project.repository;

import com.thewayhome.project.domain.ComplexImage;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface ComplexImageRepository extends JpaRepository<ComplexImage, Long> {
    @Override
    void flush();

    @Override
    <S extends ComplexImage> S saveAndFlush(S entity);

    @Override
    <S extends ComplexImage> List<S> saveAllAndFlush(Iterable<S> entities);

    @Override
    void deleteAllInBatch(Iterable<ComplexImage> entities);

    @Override
    void deleteAllByIdInBatch(Iterable<Long> longs);

    @Override
    void deleteAllInBatch();

    @Override
    ComplexImage getOne(Long aLong);

    @Override
    ComplexImage getById(Long aLong);

    @Override
    ComplexImage getReferenceById(Long aLong);

    @Override
    <S extends ComplexImage> Optional<S> findOne(Example<S> example);

    @Override
    <S extends ComplexImage> List<S> findAll(Example<S> example);

    @Override
    <S extends ComplexImage> List<S> findAll(Example<S> example, Sort sort);

    @Override
    <S extends ComplexImage> Page<S> findAll(Example<S> example, Pageable pageable);

    @Override
    <S extends ComplexImage> long count(Example<S> example);

    @Override
    <S extends ComplexImage> boolean exists(Example<S> example);

    @Override
    <S extends ComplexImage, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);

    @Override
    <S extends ComplexImage> S save(S entity);

    @Override
    <S extends ComplexImage> List<S> saveAll(Iterable<S> entities);

    @Override
    Optional<ComplexImage> findById(Long aLong);

    @Override
    boolean existsById(Long aLong);

    @Override
    List<ComplexImage> findAll();

    @Override
    List<ComplexImage> findAllById(Iterable<Long> longs);

    @Override
    long count();

    @Override
    void deleteById(Long aLong);

    @Override
    void delete(ComplexImage entity);

    @Override
    void deleteAllById(Iterable<? extends Long> longs);

    @Override
    void deleteAll(Iterable<? extends ComplexImage> entities);

    @Override
    void deleteAll();

    @Override
    List<ComplexImage> findAll(Sort sort);

    @Override
    Page<ComplexImage> findAll(Pageable pageable);
}