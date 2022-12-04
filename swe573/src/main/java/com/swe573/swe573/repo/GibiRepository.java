package com.swe573.swe573.repo;

import com.swe573.swe573.model.Gibi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GibiRepository extends JpaRepository<Gibi,Long> {
    //ya bi interface oluşturulacak , @Query ile join table yapıp return'e interfacei veriyoruz
    //ayrı bi class oluşturuyoruz.@Query ile join table yapıyoruz,
   /* @Query("select new com.swe573.swe573.model.dto.DummyDTO(g.URL,g.onComment) from Gibi g ")
    DummyDTO biseyler();

    @Query("SELECT new net.snitechnology.snicommon.dto.AnalyticResponseDTO(e.companyId, e.doctypeId, e.status, e.createdAt, COUNT(e)) " +
            "FROM ReportHeader e " +
            "WHERE e.companyId IN :companyList " +
            "AND  e.createdAt BETWEEN :start AND :end " +
            "GROUP BY e.companyId, e.createdAt, e.doctypeId, e.status")
    List<AnalyticResponseDTO> analyticQuery(List<String> companyList, LocalDateTime start, LocalDateTime end);*/


}
