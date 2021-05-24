package mycorda.app.injectable

import net.corda.core.contracts.Attachment
import net.corda.core.contracts.CommandData
import net.corda.core.contracts.CommandWithParties
import net.corda.core.contracts.ContractState
import net.corda.core.contracts.StateAndRef
import net.corda.core.contracts.TransactionState
import net.corda.core.identity.Party
import net.corda.core.internal.castIfPossible
import net.corda.core.internal.uncheckedCast
import net.corda.core.node.NetworkParameters
import net.corda.core.transactions.LedgerTransaction

/**
 * Mirror LedgerTransaction, but lets us do dependency injection
 */
interface InjectableLedgerTransaction {
    // mirror BaseTransaction
    val inputs: List<StateAndRef<ContractState>>
    val references: List<StateAndRef<ContractState>>
    val networkParameters: NetworkParameters?
    val outputs: List<TransactionState<ContractState>>
    val notary: Party?

    fun <T : ContractState> outputsOfType(clazz: Class<T>): List<T> = outputs.mapNotNull { clazz.castIfPossible(it.data) }
    // reified not allowed on an interface
    // what about if we make InjectableLedgerTransaction an abstract class instead?
    //inline fun <reified T : ContractState> outputsOfType(): List<T> = outputsOfType(T::class.java)


    // mirror LedgerTransaction
    val commands: List<CommandWithParties<CommandData>>
    val attachments: List<Attachment>

    val inputStates: List<ContractState> get() = inputs.map { it.state.data }
    val referenceStates: List<ContractState> get() = references.map { it.state.data }
    fun <T : ContractState> inRef(index: Int): StateAndRef<T> = uncheckedCast(inputs[index])


    fun verify()

}

class LedgerTransactionDelegate(private val delegate: LedgerTransaction) : InjectableLedgerTransaction {
    override val inputs: List<StateAndRef<ContractState>> = delegate.inputs

    override val references: List<StateAndRef<ContractState>> = delegate.references

    override val outputs: List<TransactionState<ContractState>> = delegate.outputs

    override val notary: Party? = delegate.notary

    override val networkParameters: NetworkParameters? = delegate.networkParameters

    override val commands: List<CommandWithParties<CommandData>> = delegate.commands

    override val attachments: List<Attachment> = delegate.attachments
    override fun verify() = delegate.verify()
}

class SimpleLedgerTransaction(private val builder : InjectableTransactionBuilder) : InjectableLedgerTransaction {
    override val inputs: List<StateAndRef<ContractState>>
        get() = TODO("Not yet implemented")
    override val references: List<StateAndRef<ContractState>> = builder.referenceStates() as List<StateAndRef<ContractState>>
    override val networkParameters: NetworkParameters? = null
    override val outputs: List<TransactionState<ContractState>> = builder.outputStates() as List<TransactionState<ContractState>>
    override val notary: Party? =  null
    override val commands: List<CommandWithParties<CommandData>> = builder.commands() as List<CommandWithParties<CommandData>>
    override val attachments: List<Attachment> = emptyList() // builder.attachments()
    override fun verify() {
        //attachments.forEach { println(it)}

        val x = outputs[0].contract
        println("here i am $x")
        // find the contract class

        val contract = Class.forName(x)?.newInstance() as InjectableContract

        contract.verify(this)





        // call


    }

}