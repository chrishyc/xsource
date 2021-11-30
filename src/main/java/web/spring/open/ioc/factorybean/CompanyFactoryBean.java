package web.spring.open.ioc.factorybean;

import lombok.Data;
import org.springframework.beans.factory.FactoryBean;

@Data
public class CompanyFactoryBean implements FactoryBean {
    private String companyInfo;

    @Override
    public Company getObject() throws Exception {
        Company company = new Company();
        String[] strings = companyInfo.split(",");
        company.setName(strings[0]);
        company.setAddress(strings[1]);
        company.setScale(Integer.parseInt(strings[2]));
        return company;
    }

    @Override
    public Class<?> getObjectType() {
        return Company.class;
    }
}
