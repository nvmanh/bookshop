package com.manhnv.file;

import org.springframework.data.repository.CrudRepository;

import com.manhnv.entity.Asset;

public interface IFileRepository extends CrudRepository<Asset, Long> {

}
