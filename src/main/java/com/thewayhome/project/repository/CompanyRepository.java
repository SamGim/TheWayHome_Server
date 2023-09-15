package com.thewayhome.project.repository;

import com.thewayhome.project.domain.Company;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface CompanyRepository extends JpaRepository<Company, Long>, CompanyRepositoryCustom {
    @Override
    void flush();

    @Override
    <S extends Company> S saveAndFlush(S entity);

    @Override
    <S extends Company> List<S> saveAllAndFlush(Iterable<S> entities);

    @Override
    void deleteAllInBatch(Iterable<Company> entities);

    @Override
    void deleteAllByIdInBatch(Iterable<Long> longs);

    @Override
    void deleteAllInBatch();

    @Override
    Company getOne(Long aLong);

    @Override
    Company getById(Long aLong);

    @Override
    Company getReferenceById(Long aLong);

    @Override
    <S extends Company> List<S> findAll(Example<S> example);

    @Override
    <S extends Company> List<S> findAll(Example<S> example, Sort sort);

    @Override
    <S extends Company> List<S> saveAll(Iterable<S> entities);

    @Override
    List<Company> findAll();

    @Override
    List<Company> findAllById(Iterable<Long> longs);

    @Override
    <S extends Company> S save(S entity);

    @Override
    Optional<Company> findById(Long aLong);

    @Override
    boolean existsById(Long aLong);

    @Override
    long count();

    @Override
    void deleteById(Long aLong);

    @Override
    void delete(Company entity);

    @Override
    void deleteAllById(Iterable<? extends Long> longs);

    @Override
    void deleteAll(Iterable<? extends Company> entities);

    @Override
    void deleteAll();

    @Override
    List<Company> findAll(Sort sort);

    @Override
    Page<Company> findAll(Pageable pageable);

    @Override
    <S extends Company> Optional<S> findOne(Example<S> example);

    @Override
    <S extends Company> Page<S> findAll(Example<S> example, Pageable pageable);

    @Override
    <S extends Company> long count(Example<S> example);

    @Override
    <S extends Company> boolean exists(Example<S> example);

    @Override
    <S extends Company, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);

}