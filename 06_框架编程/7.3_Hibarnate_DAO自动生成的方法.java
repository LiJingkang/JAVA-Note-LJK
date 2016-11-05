* save()
    方法提供了向数据库中添加数据的功能,但只能添加,这个 DAO 没有生成 Update() 的方法

    但你可以简单的八 save() 方法改称具有 Update 功能:
        将 getSession().save * (transientInstance);
        这句改成 getSession().merge(transientInstance); 或者
                getSession().saveOrUpdate(transientInstance); 

    * 例子
        public void save(User transientInstance) {
          log.debug("saving User instance");
          try {
           Session session=getSession();
           Transaction tx=session.beginTransaction();
           session.save(transientInstance);
           tx.commit();
           session.close();
           log.debug("save successful");
          } catch (RuntimeException re) {
           log.error("save failed", re);
           throw re;
          }
         }

* delete()
    方法用来删除的 实际上我们会用下边的这个方法进行删除

        public void delete(Integer id){
          log.debug("deleting User instance…");
          User user=findById(id);
          delete(user);
         }
 
         public void delete(User persistentInstance) {
          log.debug("deleting User instance");
          try {
           Session session=getSession();
           Transaction tx=session.beginTransaction();
           session.delete(persistentInstance);
           tx.commit();
           session.close();
           log.debug("delete successful");
          } catch (RuntimeException re) {
           log.error("delete failed", re);
           throw re;
          }
         }

* 根据编号进行查找

         public User findById(java.lang.Integer id) {
          log.debug("getting User instance with id: " + id);
          try {
           User instance = (User) getSession().get("hbm.User", id);
           return instance;
          } catch (RuntimeException re) {
           log.error("get failed", re);
           throw re;
          }
         }

* findByExample() 方法实现的功能相当于 "select * from Usertable" 实现的功能就是查询所有数据.

         public List findByExample(User instance) {
          log.debug("finding User instance by example");
          try {
           List results = getSession().createCriteria("hbm.User").add(
             Example.create(instance)).list();
           log.debug("find by example successful, result size: "
             + results.size());
           return results;
          } catch (RuntimeException re) {
           log.error("find by example failed", re);
           throw re;
          }
         }

* findByProperty() 方法用来灵活的提供一种按条件查询的方法,你可以自己定义要按什么样的方式查询.

         public List findByProperty(String propertyName, Object value) {
          log.debug("finding User instance with property: " + propertyName
            + ", value: " + value);
          try {
           String queryString = "from User as model where model."
             + propertyName + "= ?";
           Query queryObject = getSession().createQuery(queryString);
           queryObject.setParameter(0, value);
           return queryObject.list();
          } catch (RuntimeException re) {
           log.error("find by property name failed", re);
           throw re;
          }
         }

         public List findByName(Object name) {
          return findByProperty(NAME, name);
         }

         public List findBySex(Object sex) {
          return findByProperty(SEX, sex);
         }

         public List findByAge(Object age) {
          return findByProperty(AGE, age);
         }

         public List findAll() {
          log.debug("finding all User instances");
          try {
           String queryString = "from User";
           Query queryObject = getSession().createQuery(queryString);
           return queryObject.list();
          } catch (RuntimeException re) {
           log.error("find all failed", re);
           throw re;
          }
         }

* 将传入的 detached 状态的对象的属性复制到持久化对象中，并返回该持久化对象 
    如果该 session 中没有关联的持久化对象，加载一个，如果传入对象未保存，
    保存一个副本并作为持久对象返回，传入对象依然保持detached状态。 

* 可以用作更新数据

     public User merge(User detachedInstance) {
      log.debug("merging User instance");
      try {

        Session session=getSession();
       Transaction tx=session.beginTransaction();
       
       User result = (User) session.merge(detachedInstance);
       tx.commit();
       session.close();
       log.debug("merge successful");
       return result;
      } catch (RuntimeException re) {
       log.error("merge failed", re);
       throw re;
      }
     }

* 将传入的对象持久化并保存。 
    如果对象未保存（Transient状态），调用 save 方法保存。
    如果对象已保存（Detached状态），调用 update 方法将对象与 Session 重新关联。

     public void attachDirty(User instance) {
      log.debug("attaching dirty User instance");
      try {
       getSession().saveOrUpdate(instance);
       log.debug("attach successful");
      } catch (RuntimeException re) {
       log.error("attach failed", re);
       throw re;
      }
     }

* 将传入的对象状态设置为Transient状态 

     public void attachClean(User instance) {
      log.debug("attaching clean User instance");
      try {
       getSession().lock(instance, LockMode.NONE);
       log.debug("attach successful");
      } catch (RuntimeException re) {
       log.error("attach failed", re);
       throw re;
      }
     }