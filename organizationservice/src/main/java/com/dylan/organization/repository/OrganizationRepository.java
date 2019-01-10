package com.dylan.organization.repository;

import com.dylan.organization.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrganizationRepository extends CrudRepository<Organization,String> {
}
