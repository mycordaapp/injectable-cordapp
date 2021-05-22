import net.corda.core.identity.CordaX500Name
import net.corda.testing.common.internal.testNetworkParameters
import net.corda.testing.core.TestIdentity
import net.corda.testing.node.MockServices
import org.junit.Test

/**
 * There is no contract logic to test, but we do we
 * to check that the cordapp jar is being packaged
 * correctly.
 */
class InjectableContractTest {
    private val OPERATOR = TestIdentity(CordaX500Name.parse("O=Operator, L=London, C=GB"))

    private var ledgerServices = MockServices(
        cordappPackages = listOf("mycorda.app.injectable"),
        firstIdentity = OPERATOR,
        networkParameters = testNetworkParameters(minimumPlatformVersion = 4)
    )

    @Test
    fun `dummy test to prove the jar is built`() {
        ledgerServices.attachments.files.forEach {println (it)}
        println("great, the contract jar hae been built")
    }
}
