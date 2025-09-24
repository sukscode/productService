package dev.sukriti.productservice.InheritenceExample.SingleClass;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SCMentorRepository extends JpaRepository<Mentor, Long> {

    Mentor save(Mentor mentor);

    Mentor findMentorByUserId(Long userId);
}