package com.komoju.demo.data.models

import com.komoju.demo.domain.models.DomainPaymentStatus
import org.junit.Test

class PaymentStatusMappingTests {

    @Test
    fun `test mapping PENDING status`() {
        //given
        val paymentStatusField = "PENDING"

        //when
        val paymentStatus = paymentStatusField.toDomainPaymentStatus()

        //then
        assert(paymentStatus == DomainPaymentStatus.PENDING)
    }

    @Test
    fun `test mapping PAID status`() {
        //given
        val paymentStatusField = "PAID"

        //when
        val paymentStatus = paymentStatusField.toDomainPaymentStatus()

        //then
        assert(paymentStatus == DomainPaymentStatus.PAID)
    }

    @Test
    fun `test mapping EXPIRED status`() {
        //given
        val paymentStatusField = "EXPIRED"

        //when
        val paymentStatus = paymentStatusField.toDomainPaymentStatus()

        //then
        assert(paymentStatus == DomainPaymentStatus.EXPIRED)
    }

    @Test
    fun `test mapping FAILED status`() {
        //given
        val paymentStatusField = "FAILED"

        //when
        val paymentStatus = paymentStatusField.toDomainPaymentStatus()

        //then
        assert(paymentStatus == DomainPaymentStatus.FAILED)
    }

    @Test
    fun `test mapping unexpected status`() {
        //given
        val paymentStatusField = "NEW_UNEXPECTED_STATUS"

        //when
        val paymentStatus = paymentStatusField.toDomainPaymentStatus()

        //then
        assert(paymentStatus == DomainPaymentStatus.UNKNOWN)
    }
}