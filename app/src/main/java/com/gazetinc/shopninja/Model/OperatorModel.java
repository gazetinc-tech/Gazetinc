package com.gazetinc.shopninja.Model;

public class OperatorModel
{
    private String operator_name;
    private String operator_type;
    private String operator_code;

    public OperatorModel()
    {

    }

    public OperatorModel(String operator_name, String operator_type, String operator_code) {
        this.operator_name = operator_name;
        this.operator_type = operator_type;
        this.operator_code = operator_code;
    }


    public String getOperator_name() {
        return operator_name;
    }

    public void setOperator_name(String operator_name) {
        this.operator_name = operator_name;
    }

    public String getOperator_type() {
        return operator_type;
    }

    public void setOperator_type(String operator_type) {
        this.operator_type = operator_type;
    }

    public String getOperator_code() {
        return operator_code;
    }

    public void setOperator_code(String operator_code) {
        this.operator_code = operator_code;
    }

    @Override
    public String toString() {
        return "OperatorModel{" +
                "operator_name='" + operator_name + '\'' +
                ", operator_type='" + operator_type + '\'' +
                ", operator_code='" + operator_code + '\'' +
                '}';
    }
}
