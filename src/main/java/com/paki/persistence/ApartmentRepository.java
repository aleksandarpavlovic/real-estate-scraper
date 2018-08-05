package com.paki.persistence;

import com.paki.realties.Apartment;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentRepository extends RealtyRepository<Apartment> {}
