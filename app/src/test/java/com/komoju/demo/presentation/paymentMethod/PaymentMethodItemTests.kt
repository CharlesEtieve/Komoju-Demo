package com.komoju.demo.presentation.paymentMethod

import com.komoju.demo.domain.models.DomainPaymentMethod
import org.junit.Test

class PaymentMethodItemTests {

    @Test
    fun `test mapping PAYPAY to UI item`() {
        //given
        val paymentMethod = DomainPaymentMethod.PAYPAY

        //when
        val item = paymentMethod.toUIItem()

        //then
        assert(item.id == "PAYPAY")
        assert(item.name == "PayPay")
        assert(item.paymentMethod == DomainPaymentMethod.PAYPAY)
    }

    @Test
    fun `test mapping RAKUTEN_PAY to UI item`() {
        //given
        val paymentMethod = DomainPaymentMethod.RAKUTEN_PAY

        //when
        val item = paymentMethod.toUIItem()

        //then
        assert(item.id == "RAKUTEN_PAY")
        assert(item.name == "Rakuten Pay")
        assert(item.paymentMethod == DomainPaymentMethod.RAKUTEN_PAY)
    }

    @Test
    fun `test mapping ALIPAY to UI item`() {
        //given
        val paymentMethod = DomainPaymentMethod.ALIPAY

        //when
        val item = paymentMethod.toUIItem()

        //then
        assert(item.id == "ALIPAY")
        assert(item.name == "Alipay")
        assert(item.paymentMethod == DomainPaymentMethod.ALIPAY)
    }

    @Test
    fun `test mapping LINEPAY to UI item`() {
        //given
        val paymentMethod = DomainPaymentMethod.LINEPAY

        //when
        val item = paymentMethod.toUIItem()

        //then
        assert(item.id == "LINEPAY")
        assert(item.name == "LINE Pay")
        assert(item.paymentMethod == DomainPaymentMethod.LINEPAY)
    }

    @Test
    fun `test mapping UNKNOWN to UI item`() {
        //given
        val paymentMethod = DomainPaymentMethod.UNKNOWN

        //when
        val item = paymentMethod.toUIItem()

        //then
        assert(item.id == "UNKNOWN")
        assert(item.name == "Unknown")
        assert(item.paymentMethod == DomainPaymentMethod.UNKNOWN)
    }
}
