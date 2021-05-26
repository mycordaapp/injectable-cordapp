README
======

This little CorDapp is the foundation for building CorDapps 
in a more modular way to facilitate easier unit testing of contracts
and flows

In Corda the two key concepts, TransactionBuilder and  Contract are implemented
as Kotlin classes only and only partially open for extension. This limits unit 
testing. 

In this project we simply define two parallel interfaces, 
`InjectableTransactionBuilder` and `InjectableContract` and provide 
default implementations that simple delegate to the native Corda 
implementation. This "unlocks" the internals of Corda to allow for a 
suite of lighter weight mock than those shipped with Corda. 

Note that this has be deployed as a CorDapp to make the `InjectableContract` 
visible to any CorDapps that depend upon it. 
