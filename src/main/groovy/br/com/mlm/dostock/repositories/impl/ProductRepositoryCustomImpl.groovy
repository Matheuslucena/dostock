package br.com.mlm.dostock.repositories.impl

import br.com.mlm.dostock.domain.Product
import br.com.mlm.dostock.repositories.ProductRepositoryCustom
import org.springframework.stereotype.Service

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.Query

@Service
class ProductRepositoryCustomImpl implements ProductRepositoryCustom{
    @PersistenceContext
    EntityManager entityManager

    @Override
    List<Product> search(String name, String code, Long tagId, Long folderId) {
        String sql = $/
            FROM Product p
                ${folderId ? ' JOIN p.productFolders pf': '' }
                ${tagId ? ' JOIN p.tags t': ''}
                WHERE
                    deleted = false
                    ${name ? " AND LOWER(name) like :name" : ''}
                    ${code ? " AND LOWER(code) like :code" : ''}
                    ${tagId ? " AND t.id = :tagId" : ''}
                    ${folderId ? " AND pf.folder.id = :folderId" : ''}
                    order by name
        /$

        Query query =  entityManager.createQuery(sql)

        if(name) query.setParameter("name", name)
        if(code) query.setParameter("code", code)
        if(tagId) query.setParameter("tagId", tagId)
        if(folderId) query.setParameter("folderId", folderId)

        entityManager.close()

        return query.getResultList()
    }
}
