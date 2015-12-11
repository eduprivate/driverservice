package br.com.edu.driver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.edu.driver.model.Driver;

public interface DriverRepository extends JpaRepository<Driver, Long> {

   Driver findById(Long id);
   
   @Query(value="select * from drivers d where d.latitude >= :swLatitute and d.latitude <= :neLatitute and d.longitude >= :swLongitude and d.longitude <= :neLongitude",nativeQuery = true)
   List<Driver> inArea(@Param("swLatitute") Double swLatitute,
                                  @Param("neLatitute") Double neLatitute, 
                                  @Param("swLongitude") Double swLongitude,
                                  @Param("neLongitude") Double neLongitude
                                  );
   
}
