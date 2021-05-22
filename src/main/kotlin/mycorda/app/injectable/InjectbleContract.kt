package mycorda.app.injectable

import net.corda.core.contracts.Contract
import net.corda.core.transactions.LedgerTransaction

/**
 * We cannot inject a LedgerTransaction easily (it is a closed class,
 * not an Interface). This makes DI hard, as it complex job building
 * a LedgerTransaction that it linked to mocked service. Instead we use
 * InjectableLedgerTransaction (basically an interface that matches LedgerTransaction
 * method to method.
 */
open class InjectableContract : Contract {
    companion object {
        @JvmStatic
        val ID = "mycorda.app.injectable.InjectableContract"
    }
    // entry point when running in TDD environment - there is a lightweight
    // InjectableLedgerTransaction
    open fun verify(tx: InjectableLedgerTransaction) {
        TODO()
    }

    // entry point when running in Corda - InjectableLedgerTransaction
    // simple delegates to the real LedgerTransaction
    override fun verify(tx: LedgerTransaction) {
        verify(LedgerTransactionDelegate(tx))
    }
}