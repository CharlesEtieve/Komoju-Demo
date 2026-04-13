# Plan

You gave me 3 hours so here is my plan to complete it :

- Create Android project and add dependencies for Kotlin, Coroutine Flow, Ktor and Koin (15 min)
- Create DomainPaymentMethod, DomainPaymentSession, DomainPaymentStatus, DomainAmount and DomainException models (15 min)
- Create PaymentMethodRepository (interface and impl) and Api with getPaymentMethodList method (15 min)
- Create PaymentService (interface and impl) and Api with createPaymentSession, getPaymentStatus methods (15 min)
- Create Base64ToBitmapHelper (5 min)
- Create withRetry utils (5 min)- Create PaymentMethodScreen and viewModel (10 min)
- Create PaymentAmountScreen and viewModel (10 min)
- Create PaymentQRCodeScreen and viewModel (10 min)
- Create PaymentCompletionScreen (10 min)
- Create PaymentErrorScreen (10 min)- Debug (30 min)
- Debug (30 min)
- Add unit tests on DomainPaymentStatus mapping (15 min)

# Technical remark

The function mockPayment will not be exposed by PaymentService, it will be an internal function.
The function will be available for development variant only.

For this use case retry api call will intentionally be handled at the Flow level, not via an OkHttp interceptor. I will create a withRetry utils to handle this.

I will use Claude to make the screens because you seem more interested in my architecture.

To run unit tests, launch this command : ./gradlew :app:testDevelopmentDebugUnitTest

I need also to tell you that a REST API to listen for payment status updates is not the best option.
For best performance and convenience I would use GRPC to listen for change.

# UX remark

I will remove the radio buttons because one tap to select the payment method is enough.
The merchant will do payment oftenly, it must have an efficient UX.

I will update button label to better reflect call to action, "Continue" will become "Generate QR Code".
