package reportengine;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("/payslip")
public class PayslipController {

    @Get("/")
    public List<Payslip> index() {

        List<Payslip> payslips = new ArrayList<>();
        Payslip payslip = new Payslip();
        payslip.setId(1);
        payslip.setName("Harish Babu B K");
        payslips.add(payslip);

        try {
            createPdfReport(payslips);

        } catch (final Exception e) {

            e.printStackTrace();
        }

        return payslips;
    }


    private void createPdfReport(List<Payslip> payslips) throws JRException {

        final InputStream stream = this.getClass().getResourceAsStream("/report.jrxml");

        final JasperReport report = JasperCompileManager.compileReport(stream);


        final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(payslips);

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "javacodegeek.com");
        final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);
        final String filePath = "Harish_Babu";
        JasperExportManager.exportReportToPdfFile(print, filePath + ".pdf");

    }


}