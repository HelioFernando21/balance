package com.bank.balance.domain.operationType;

public enum OperationTypeEnum {
    NORMAL_PURCHASE(1L),
    PURCHASE_WITH_INSTALLMENTS(2L),
    WITHDRAWAL(3L),
    CREDIT_VOUCHER(4L);

    private Long id;
    OperationTypeEnum(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static boolean contains(Long id) {
        for (OperationTypeEnum type : OperationTypeEnum.values()) {
            if (type.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
