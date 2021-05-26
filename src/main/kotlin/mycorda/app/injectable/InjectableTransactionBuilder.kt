package mycorda.app.injectable

import net.corda.core.contracts.AttachmentConstraint
import net.corda.core.contracts.AutomaticPlaceholderConstraint
import net.corda.core.contracts.Command
import net.corda.core.contracts.CommandData
import net.corda.core.contracts.ContractClassName
import net.corda.core.contracts.ContractState
import net.corda.core.contracts.PrivacySalt
import net.corda.core.contracts.ReferencedStateAndRef
import net.corda.core.contracts.StateAndRef
import net.corda.core.contracts.StateRef
import net.corda.core.contracts.TimeWindow
import net.corda.core.contracts.TransactionState
import net.corda.core.identity.Party
import net.corda.core.internal.requiredContractClassName
import net.corda.core.node.ServiceHub
import net.corda.core.node.services.AttachmentId
import net.corda.core.transactions.TransactionBuilder
import java.security.PublicKey

/**
 * Mirrors the API of the actual TransactionBuilder - lets
 * us do dependency injection.
 *
 * This interface also exposes all transaction state, making it more generic
 * in it usage. The real TransactionBuilder is only capable of converting to
 * a WireTransaction
 */
interface InjectableTransactionBuilder {

    fun copy(): InjectableTransactionBuilder

    fun addInputState(stateAndRef: StateAndRef<*>): InjectableTransactionBuilder

    fun addOutputState(state: TransactionState<*>): InjectableTransactionBuilder

    fun addOutputState(
        state: ContractState,
        contract: ContractClassName = state.requiredContractClassName!!,
        notary: Party,
        encumbrance: Int? = null,
        constraint: AttachmentConstraint = AutomaticPlaceholderConstraint
    ): InjectableTransactionBuilder

    fun addOutputState(
        state: ContractState,
        contract: ContractClassName = state.requiredContractClassName!!,
        constraint: AttachmentConstraint = AutomaticPlaceholderConstraint
    ): InjectableTransactionBuilder

//    fun addOutputState(
//        state: ContractState,
//        contract: ContractClassName = requireNotNullContractClassName(state),
//        constraint: AttachmentConstraint = AutomaticPlaceholderConstraint
//    ): SimpleTransactionBuilder {
//        checkNotNull(notary) { "Need to specify a notary for the state, or set a default one on TransactionBuilder initialisation" }
//        addOutputState(state, contract, notary!!, constraint = constraint)
//        return this
//    }

    fun addReferenceState(referencedStateAndRef: ReferencedStateAndRef<*>): InjectableTransactionBuilder

    fun addAttachment(attachmentId: AttachmentId): InjectableTransactionBuilder

    fun addCommand(arg: Command<*>): InjectableTransactionBuilder

    fun addCommand(data: CommandData, keys: List<PublicKey>) : InjectableTransactionBuilder

    fun addCommand(data: CommandData, vararg keys: PublicKey) = addCommand(Command(data, listOf(*keys)))

    fun setTimeWindow(timeWindow: TimeWindow): InjectableTransactionBuilder

    fun setPrivacySalt(privacySalt: PrivacySalt): InjectableTransactionBuilder

    fun withItems(vararg items: Any): InjectableTransactionBuilder

    /** Returns an immutable list of input [StateRef]s. */
    fun inputStates(): List<StateRef>

    /** Returns an immutable list of reference input [StateRef]s. */
    fun referenceStates(): List<StateRef>

    /** Returns an immutable list of attachment hashes. */
    fun attachments(): List<AttachmentId>

    /** Returns an immutable list of output [TransactionState]s. */
    fun outputStates(): List<TransactionState<*>>

    /** Returns an immutable list of [Command]s. */
    fun commands(): List<Command<*>>

    fun verify(services: ServiceHub)


}

interface CordaTransactionBuilder {
    /** if running inside a flow, then we
     *  need to get the real TransactionBuilder
     *
     *  If running inside the mock / adapter layer this will
     *  raise an exception
     */
    fun asCordaTransactionBuilder () : TransactionBuilder
}
