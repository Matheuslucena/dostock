package br.com.mlm.dostock.repositories.impl

import br.com.mlm.dostock.domain.Product
import br.com.mlm.dostock.domain.ProductLog
import br.com.mlm.dostock.repositories.ProductLogRepositoryCustom
import br.com.mlm.dostock.util.types.ProductLogType
import org.springframework.stereotype.Service

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.Query
import javax.persistence.TemporalType

@Service
class ProductLogRepositoryCustomImpl implements ProductLogRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager

    @Override
    List<ProductLog> search(Date dateInitial, Date dateFinal, Product product, ProductLogType productLogType) {
        String sql = $/
            FROM ProductLog
                WHERE
                    logType = :logType
                AND
                    dateCreated between :dateInitial and :dateFinal
                ${product ? " AND product.id = :product" : ''}
        /$

        Query query =  entityManager.createQuery(sql)
        query.setParameter("logType", productLogType)
        query.setParameter("dateInitial", dateInitial, TemporalType.TIMESTAMP)
        query.setParameter("dateFinal", dateFinal, TemporalType.TIMESTAMP)

        if(product) query.setParameter("product", product.id)

        entityManager.close()

        return query.getResultList()
    }
}
