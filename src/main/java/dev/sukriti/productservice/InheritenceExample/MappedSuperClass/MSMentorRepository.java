package dev.sukriti.productservice.InheritenceExample.MappedSuperClass;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MSMentorRepository extends JpaRepository<Mentor, Long> {

    Mentor save(Mentor mentor);

    Mentor findMentorByUserId(Long userId);
}
