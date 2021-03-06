package com.kinoshita.springboot.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.kinoshita.springboot.dto.CustomerSearchConditions;
import com.kinoshita.springboot.entity.Customer;
import com.kinoshita.springboot.repository.CustomerRepository;

@Service
public class CustomerService {

	/**
	 * 1ページに表示するエンティティ数
	 */
	private static final int PAGE_SIZE = 20;
	
	@Autowired
	CustomerRepository repository;
	
	@PersistenceContext
	private EntityManager em;
	
	
	public static int getPAGE_SIZE() {
		return PAGE_SIZE;
	}
	
	/**
	 * 動的クエリで顧客検索、ページネーション
	 * @param name
	 * @param kana
	 * @param postal_code
	 * @param address1
	 * @param address2
	 * @param tel
	 * @param fax
	 * @return
	 */
	public Page<Customer> findCustomers(CustomerSearchConditions condition, Integer pageNumber) {
		PageRequest pageRequest = new PageRequest(pageNumber - 1, PAGE_SIZE);
		Page<Customer> results = repository.findAll(Specifications
				.where(CustomerSpecifications.nameContains(condition.getName()))
				.and(CustomerSpecifications.kanaContains(condition.getKana()))
				.and(CustomerSpecifications.postalCodeContains(condition.getPostalCode()))
				.and(CustomerSpecifications.address1Contains(condition.getAddress1()))
				.and(CustomerSpecifications.address2Contains(condition.getAddress2()))
				.and(CustomerSpecifications.telContains(condition.getTel()))
				.and(CustomerSpecifications.faxContains(condition.getFax()))
				.and(CustomerSpecifications.deletedIsNull())
				, pageRequest);
		return results;
	}
	
}
