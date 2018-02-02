package com.epam.rd.november2017.vlasenko.dao.transaction;

import javax.sql.DataSource;
import java.sql.SQLException;

public interface TransactionHandler extends DataSource {
    <T> T doInTransaction (TransactionBody<T> unitOfWork) throws SQLException;
}
