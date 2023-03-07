package groovy

import com.glowbyte.practicaltask.config.DBConfig
import com.glowbyte.practicaltask.entity.Address
import com.glowbyte.practicaltask.entity.Application
import com.glowbyte.practicaltask.entity.Client
import com.glowbyte.practicaltask.entity.Income
import com.glowbyte.practicaltask.groovy.DBBuilder

import groovy.sql.Sql
import org.springframework.context.annotation.AnnotationConfigApplicationContext


def context = new AnnotationConfigApplicationContext(DBConfig.class)
def dataSource = context.getBean("dataSource")
def sql = new Sql(dataSource)

@GrabConfig(initContextClassLoader = true)
static Application transform(def sql, int id) {
    Application result = null
    Map<BigDecimal, Set<Address>> clientAddresses = new HashMap<>()
    Map<BigDecimal, Set<Income>> applicationIncomes = new HashMap<>()
    Map<BigDecimal, Set<Client>> applicationClient = new HashMap<>()

    // Сбор адрессов для клиентов
    sql.query('select * from address where application_id = ' + id + ';') { resultSet ->
        while (resultSet.next()) {
            BigDecimal key = resultSet.getBigDecimal("client_id")
            if (clientAddresses.containsKey(key)) {
                clientAddresses.get(key).add(
                        DBBuilder.buildAddress(resultSet)
                )
            } else {
                Set<Address> addresses = new HashSet<>()
                addresses.add(
                        DBBuilder.buildAddress(resultSet)
                )
                clientAddresses.put(
                        key,
                        addresses
                )
            }
        }
    }

// сбор клиентов для приложений
    sql.query('select * from client where application_id = ' + id + ';') { resultSet ->
        while (resultSet.next()) {
            BigDecimal key = resultSet.getBigDecimal("application_id")
            if (applicationClient.containsKey(key)) {
                applicationClient.get(key).add(
                        DBBuilder.buildClient(resultSet, clientAddresses)
                )
            } else {
                Set<Client> clients = new HashSet<>()
                clients.add(
                        DBBuilder.buildClient(resultSet, clientAddresses)
                )
                applicationClient.put(key, clients)
            }
        }
    }

    // Сбор дохода для приложений
    sql.query('select * from income where application_id = ' + id + ';') { resultSet ->
        while (resultSet.next()) {
            BigDecimal key = resultSet.getBigDecimal("application_id")
            if (applicationIncomes.containsKey(key)) {
                applicationIncomes.get(key).add(
                        DBBuilder.buildIncome(resultSet)
                )
            } else {
                Set<Income> incomes = new HashSet<>()
                incomes.add(
                        DBBuilder.buildIncome(resultSet)
                )
                applicationIncomes.put(key, incomes)
            }
        }
    }

    // Сбор в result
    sql.query('select * from application where application_id = ' + id + ';') { resultSet ->
        while (resultSet.next()) {
            result = DBBuilder.buildApplication(resultSet, applicationClient, applicationIncomes)
        }
    }

    return result
}

def result = transform(sql, 1)