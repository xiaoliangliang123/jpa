package com.jpa.overwrite.service.impl;

import com.jpa.overwrite.entity.Table1;
import com.jpa.overwrite.repository.TableRepository;
import com.jpa.overwrite.service.TableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TableServiceImpl implements TableService {

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Table1 getTable1(Integer id) {
        log.info("id:{}",id);
        return tableRepository.findById(id).get();
    }

    @Override
    public Page<Table1> getAllTable1ByPage(int page) {


//        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
//        CriteriaQuery<Table1>criteriaQuery=criteriaBuilder.createQuery(Table1.class);
//        Metamodel metamodel=entityManager.getMetamodel();
//        EntityType<Table1> entityType=metamodel.entity(Table1.class);
//        Root<Table1>root=criteriaQuery.from(entityType);
//
//        Expression expression =  criteriaBuilder.selectCase().when(criteriaBuilder.equal(root.get("title"),"哈哈"),"哦也").otherwise("飒飒");
//        criteriaQuery.select(expression);
//        TypedQuery<Table1> typedQuery = entityManager.createQuery(criteriaQuery);
//        List<Table1> table1s = typedQuery.getResultList();

        Specification<Table1> specification = new Specification<Table1>() {

            @Nullable
            @Override
            public Predicate toPredicate(Root<Table1> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

               return null;
            }
        };

        if (page == 0){
            page = 1;
        }
        Pageable pageable =  PageRequest.of(page - 1, 2);

        Page<Table1> table1s = tableRepository.findAll(specification,pageable);

        List<Table1>  tbs = table1s.stream().filter(
                s->{
            if(s.getTitle().equals("哈哈")&&dateCompareTo(s.getTime())){

                s.setTitle("replease");
            }
            return true;
        }).collect(Collectors.toList());

        return new PageImpl(tbs,pageable,table1s.getTotalElements());
    }

    private boolean dateCompareTo(String ds1){

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date d1 =  sdf.parse(ds1);
            Date d2 =  new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(d1);
            cal.add(Calendar.HOUR, 1);// 24小时制
            d1 = cal.getTime();

            return  d1.compareTo(d2)<1;
        }catch (Exception e){
           return false;
        }

    }
}
