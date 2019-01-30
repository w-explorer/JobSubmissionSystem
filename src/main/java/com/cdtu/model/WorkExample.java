package com.cdtu.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class WorkExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WorkExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPwIdIsNull() {
            addCriterion("pw_id is null");
            return (Criteria) this;
        }

        public Criteria andPwIdIsNotNull() {
            addCriterion("pw_id is not null");
            return (Criteria) this;
        }

        public Criteria andPwIdEqualTo(String value) {
            addCriterion("pw_id =", value, "pwId");
            return (Criteria) this;
        }

        public Criteria andPwIdNotEqualTo(String value) {
            addCriterion("pw_id <>", value, "pwId");
            return (Criteria) this;
        }

        public Criteria andPwIdGreaterThan(String value) {
            addCriterion("pw_id >", value, "pwId");
            return (Criteria) this;
        }

        public Criteria andPwIdGreaterThanOrEqualTo(String value) {
            addCriterion("pw_id >=", value, "pwId");
            return (Criteria) this;
        }

        public Criteria andPwIdLessThan(String value) {
            addCriterion("pw_id <", value, "pwId");
            return (Criteria) this;
        }

        public Criteria andPwIdLessThanOrEqualTo(String value) {
            addCriterion("pw_id <=", value, "pwId");
            return (Criteria) this;
        }

        public Criteria andPwIdLike(String value) {
            addCriterion("pw_id like", value, "pwId");
            return (Criteria) this;
        }

        public Criteria andPwIdNotLike(String value) {
            addCriterion("pw_id not like", value, "pwId");
            return (Criteria) this;
        }

        public Criteria andPwIdIn(List<String> values) {
            addCriterion("pw_id in", values, "pwId");
            return (Criteria) this;
        }

        public Criteria andPwIdNotIn(List<String> values) {
            addCriterion("pw_id not in", values, "pwId");
            return (Criteria) this;
        }

        public Criteria andPwIdBetween(String value1, String value2) {
            addCriterion("pw_id between", value1, value2, "pwId");
            return (Criteria) this;
        }

        public Criteria andPwIdNotBetween(String value1, String value2) {
            addCriterion("pw_id not between", value1, value2, "pwId");
            return (Criteria) this;
        }

        public Criteria andWTimeIsNull() {
            addCriterion("w_time is null");
            return (Criteria) this;
        }

        public Criteria andWTimeIsNotNull() {
            addCriterion("w_time is not null");
            return (Criteria) this;
        }

        public Criteria andWTimeEqualTo(Date value) {
            addCriterionForJDBCDate("w_time =", value, "wTime");
            return (Criteria) this;
        }

        public Criteria andWTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("w_time <>", value, "wTime");
            return (Criteria) this;
        }

        public Criteria andWTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("w_time >", value, "wTime");
            return (Criteria) this;
        }

        public Criteria andWTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("w_time >=", value, "wTime");
            return (Criteria) this;
        }

        public Criteria andWTimeLessThan(Date value) {
            addCriterionForJDBCDate("w_time <", value, "wTime");
            return (Criteria) this;
        }

        public Criteria andWTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("w_time <=", value, "wTime");
            return (Criteria) this;
        }

        public Criteria andWTimeIn(List<Date> values) {
            addCriterionForJDBCDate("w_time in", values, "wTime");
            return (Criteria) this;
        }

        public Criteria andWTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("w_time not in", values, "wTime");
            return (Criteria) this;
        }

        public Criteria andWTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("w_time between", value1, value2, "wTime");
            return (Criteria) this;
        }

        public Criteria andWTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("w_time not between", value1, value2, "wTime");
            return (Criteria) this;
        }

        public Criteria andWContextIsNull() {
            addCriterion("w_context is null");
            return (Criteria) this;
        }

        public Criteria andWContextIsNotNull() {
            addCriterion("w_context is not null");
            return (Criteria) this;
        }

        public Criteria andWContextEqualTo(String value) {
            addCriterion("w_context =", value, "wContext");
            return (Criteria) this;
        }

        public Criteria andWContextNotEqualTo(String value) {
            addCriterion("w_context <>", value, "wContext");
            return (Criteria) this;
        }

        public Criteria andWContextGreaterThan(String value) {
            addCriterion("w_context >", value, "wContext");
            return (Criteria) this;
        }

        public Criteria andWContextGreaterThanOrEqualTo(String value) {
            addCriterion("w_context >=", value, "wContext");
            return (Criteria) this;
        }

        public Criteria andWContextLessThan(String value) {
            addCriterion("w_context <", value, "wContext");
            return (Criteria) this;
        }

        public Criteria andWContextLessThanOrEqualTo(String value) {
            addCriterion("w_context <=", value, "wContext");
            return (Criteria) this;
        }

        public Criteria andWContextLike(String value) {
            addCriterion("w_context like", value, "wContext");
            return (Criteria) this;
        }

        public Criteria andWContextNotLike(String value) {
            addCriterion("w_context not like", value, "wContext");
            return (Criteria) this;
        }

        public Criteria andWContextIn(List<String> values) {
            addCriterion("w_context in", values, "wContext");
            return (Criteria) this;
        }

        public Criteria andWContextNotIn(List<String> values) {
            addCriterion("w_context not in", values, "wContext");
            return (Criteria) this;
        }

        public Criteria andWContextBetween(String value1, String value2) {
            addCriterion("w_context between", value1, value2, "wContext");
            return (Criteria) this;
        }

        public Criteria andWContextNotBetween(String value1, String value2) {
            addCriterion("w_context not between", value1, value2, "wContext");
            return (Criteria) this;
        }

        public Criteria andWAddrIsNull() {
            addCriterion("w_addr is null");
            return (Criteria) this;
        }

        public Criteria andWAddrIsNotNull() {
            addCriterion("w_addr is not null");
            return (Criteria) this;
        }

        public Criteria andWAddrEqualTo(String value) {
            addCriterion("w_addr =", value, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWAddrNotEqualTo(String value) {
            addCriterion("w_addr <>", value, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWAddrGreaterThan(String value) {
            addCriterion("w_addr >", value, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWAddrGreaterThanOrEqualTo(String value) {
            addCriterion("w_addr >=", value, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWAddrLessThan(String value) {
            addCriterion("w_addr <", value, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWAddrLessThanOrEqualTo(String value) {
            addCriterion("w_addr <=", value, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWAddrLike(String value) {
            addCriterion("w_addr like", value, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWAddrNotLike(String value) {
            addCriterion("w_addr not like", value, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWAddrIn(List<String> values) {
            addCriterion("w_addr in", values, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWAddrNotIn(List<String> values) {
            addCriterion("w_addr not in", values, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWAddrBetween(String value1, String value2) {
            addCriterion("w_addr between", value1, value2, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWAddrNotBetween(String value1, String value2) {
            addCriterion("w_addr not between", value1, value2, "wAddr");
            return (Criteria) this;
        }

        public Criteria andWProblemIsNull() {
            addCriterion("w_problem is null");
            return (Criteria) this;
        }

        public Criteria andWProblemIsNotNull() {
            addCriterion("w_problem is not null");
            return (Criteria) this;
        }

        public Criteria andWProblemEqualTo(String value) {
            addCriterion("w_problem =", value, "wProblem");
            return (Criteria) this;
        }

        public Criteria andWProblemNotEqualTo(String value) {
            addCriterion("w_problem <>", value, "wProblem");
            return (Criteria) this;
        }

        public Criteria andWProblemGreaterThan(String value) {
            addCriterion("w_problem >", value, "wProblem");
            return (Criteria) this;
        }

        public Criteria andWProblemGreaterThanOrEqualTo(String value) {
            addCriterion("w_problem >=", value, "wProblem");
            return (Criteria) this;
        }

        public Criteria andWProblemLessThan(String value) {
            addCriterion("w_problem <", value, "wProblem");
            return (Criteria) this;
        }

        public Criteria andWProblemLessThanOrEqualTo(String value) {
            addCriterion("w_problem <=", value, "wProblem");
            return (Criteria) this;
        }

        public Criteria andWProblemLike(String value) {
            addCriterion("w_problem like", value, "wProblem");
            return (Criteria) this;
        }

        public Criteria andWProblemNotLike(String value) {
            addCriterion("w_problem not like", value, "wProblem");
            return (Criteria) this;
        }

        public Criteria andWProblemIn(List<String> values) {
            addCriterion("w_problem in", values, "wProblem");
            return (Criteria) this;
        }

        public Criteria andWProblemNotIn(List<String> values) {
            addCriterion("w_problem not in", values, "wProblem");
            return (Criteria) this;
        }

        public Criteria andWProblemBetween(String value1, String value2) {
            addCriterion("w_problem between", value1, value2, "wProblem");
            return (Criteria) this;
        }

        public Criteria andWProblemNotBetween(String value1, String value2) {
            addCriterion("w_problem not between", value1, value2, "wProblem");
            return (Criteria) this;
        }

        public Criteria andWRemarkIsNull() {
            addCriterion("w_remark is null");
            return (Criteria) this;
        }

        public Criteria andWRemarkIsNotNull() {
            addCriterion("w_remark is not null");
            return (Criteria) this;
        }

        public Criteria andWRemarkEqualTo(String value) {
            addCriterion("w_remark =", value, "wRemark");
            return (Criteria) this;
        }

        public Criteria andWRemarkNotEqualTo(String value) {
            addCriterion("w_remark <>", value, "wRemark");
            return (Criteria) this;
        }

        public Criteria andWRemarkGreaterThan(String value) {
            addCriterion("w_remark >", value, "wRemark");
            return (Criteria) this;
        }

        public Criteria andWRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("w_remark >=", value, "wRemark");
            return (Criteria) this;
        }

        public Criteria andWRemarkLessThan(String value) {
            addCriterion("w_remark <", value, "wRemark");
            return (Criteria) this;
        }

        public Criteria andWRemarkLessThanOrEqualTo(String value) {
            addCriterion("w_remark <=", value, "wRemark");
            return (Criteria) this;
        }

        public Criteria andWRemarkLike(String value) {
            addCriterion("w_remark like", value, "wRemark");
            return (Criteria) this;
        }

        public Criteria andWRemarkNotLike(String value) {
            addCriterion("w_remark not like", value, "wRemark");
            return (Criteria) this;
        }

        public Criteria andWRemarkIn(List<String> values) {
            addCriterion("w_remark in", values, "wRemark");
            return (Criteria) this;
        }

        public Criteria andWRemarkNotIn(List<String> values) {
            addCriterion("w_remark not in", values, "wRemark");
            return (Criteria) this;
        }

        public Criteria andWRemarkBetween(String value1, String value2) {
            addCriterion("w_remark between", value1, value2, "wRemark");
            return (Criteria) this;
        }

        public Criteria andWRemarkNotBetween(String value1, String value2) {
            addCriterion("w_remark not between", value1, value2, "wRemark");
            return (Criteria) this;
        }

        public Criteria andSWStateIsNull() {
            addCriterion("s_w_state is null");
            return (Criteria) this;
        }

        public Criteria andSWStateIsNotNull() {
            addCriterion("s_w_state is not null");
            return (Criteria) this;
        }

        public Criteria andSWStateEqualTo(Boolean value) {
            addCriterion("s_w_state =", value, "sWState");
            return (Criteria) this;
        }

        public Criteria andSWStateNotEqualTo(Boolean value) {
            addCriterion("s_w_state <>", value, "sWState");
            return (Criteria) this;
        }

        public Criteria andSWStateGreaterThan(Boolean value) {
            addCriterion("s_w_state >", value, "sWState");
            return (Criteria) this;
        }

        public Criteria andSWStateGreaterThanOrEqualTo(Boolean value) {
            addCriterion("s_w_state >=", value, "sWState");
            return (Criteria) this;
        }

        public Criteria andSWStateLessThan(Boolean value) {
            addCriterion("s_w_state <", value, "sWState");
            return (Criteria) this;
        }

        public Criteria andSWStateLessThanOrEqualTo(Boolean value) {
            addCriterion("s_w_state <=", value, "sWState");
            return (Criteria) this;
        }

        public Criteria andSWStateIn(List<Boolean> values) {
            addCriterion("s_w_state in", values, "sWState");
            return (Criteria) this;
        }

        public Criteria andSWStateNotIn(List<Boolean> values) {
            addCriterion("s_w_state not in", values, "sWState");
            return (Criteria) this;
        }

        public Criteria andSWStateBetween(Boolean value1, Boolean value2) {
            addCriterion("s_w_state between", value1, value2, "sWState");
            return (Criteria) this;
        }

        public Criteria andSWStateNotBetween(Boolean value1, Boolean value2) {
            addCriterion("s_w_state not between", value1, value2, "sWState");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}