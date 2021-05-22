package mycorda.app.injectable

import net.corda.core.contracts.AttachmentConstraint
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
import net.corda.core.node.ServiceHub
import net.corda.core.node.services.AttachmentId
import net.corda.core.transactions.TransactionBuilder
import java.security.PublicKey

/**
 * Delegates to the genuine Corda TransactionBuilder.
 */

class TransactionBuilderDelegate(private val delegate: TransactionBuilder) : InjectableTransactionBuilder {
    override fun copy(): InjectableTransactionBuilder {
        delegate.copy()
        return this
    }

    override fun addInputState(stateAndRef: StateAndRef<*>): InjectableTransactionBuilder {
        delegate.addInputState(stateAndRef)
        return this
    }

    override fun addOutputState(state: TransactionState<*>): InjectableTransactionBuilder {
        delegate.addOutputState(state)
        return this
    }

    override fun addOutputState(
        state: ContractState,
        contract: ContractClassName,
        notary: Party,
        encumbrance: Int?,
        constraint: AttachmentConstraint
    ): InjectableTransactionBuilder {
        delegate.addOutputState(state, contract, notary, encumbrance, constraint)
        return this
    }

    override fun addOutputState(
        state: ContractState,
        contract: ContractClassName,
        constraint: AttachmentConstraint
    ): InjectableTransactionBuilder {
        delegate.addOutputState(state, contract, constraint)
        return this
    }

    override fun addReferenceState(referencedStateAndRef: ReferencedStateAndRef<*>): InjectableTransactionBuilder {
        delegate.addReferenceState(referencedStateAndRef)
        return this
    }

    override fun addAttachment(attachmentId: AttachmentId): InjectableTransactionBuilder {
        delegate.addAttachment(attachmentId)
        return this
    }

    override fun addCommand(arg: Command<*>): InjectableTransactionBuilder {
        delegate.addCommand(arg)
        return this
    }

    override fun addCommand(data: CommandData, keys: List<PublicKey>): InjectableTransactionBuilder {
        delegate.addCommand(data, keys)
        return this
    }

    override fun setTimeWindow(timeWindow: TimeWindow): InjectableTransactionBuilder {
        delegate.setTimeWindow(timeWindow)
        return this
    }

    override fun setPrivacySalt(privacySalt: PrivacySalt): InjectableTransactionBuilder {
        delegate.setPrivacySalt(privacySalt)
        return this
    }

    override fun withItems(vararg items: Any): InjectableTransactionBuilder {
        delegate.withItems(items)
        return this
    }

    override fun inputStates(): List<StateRef> = delegate.inputStates()

    override fun referenceStates(): List<StateRef> = delegate.referenceStates()

    override fun attachments(): List<AttachmentId> = delegate.attachments()

    override fun outputStates(): List<TransactionState<*>> = delegate.outputStates()

    override fun commands(): List<Command<*>> = delegate.commands()
    override fun verify(services: ServiceHub) {
        TODO("Not yet implemented")
    }

}