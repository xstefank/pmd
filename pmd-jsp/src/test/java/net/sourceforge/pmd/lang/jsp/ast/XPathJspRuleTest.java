package net.sourceforge.pmd.lang.jsp.ast;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;

import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.Report;
import net.sourceforge.pmd.Rule;
import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.RuleSet;
import net.sourceforge.pmd.RuleSets;
import net.sourceforge.pmd.RuleViolation;
import net.sourceforge.pmd.lang.LanguageRegistry;
import net.sourceforge.pmd.lang.jsp.JspLanguageModule;
import net.sourceforge.pmd.lang.rule.XPathRule;
import net.sourceforge.pmd.testframework.RuleTst;

import org.junit.Test;


public class XPathJspRuleTest extends RuleTst {

    /**
     * Test matching a XPath expression against a JSP source.
     *
     * @throws Throwable
     */
    @Test
    public void testExpressionMatching() throws Throwable {
        Rule rule = new XPathRule(XPATH_EXPRESSION);
        rule.setMessage("Test");
        rule.setLanguage(LanguageRegistry.getLanguage(JspLanguageModule.NAME));
        RuleSet rules = new RuleSet();
        rules.addRule(rule);

        RuleContext ctx = new RuleContext();
        Report report = new Report();
        ctx.setReport(report);
        ctx.setSourceCodeFilename("n/a");
        ctx.setLanguageVersion(LanguageRegistry.getLanguage(JspLanguageModule.NAME).getDefaultVersion());

        PMD p = new PMD();

        p.getSourceCodeProcessor().processSourceCode(new StringReader(MATCH), new RuleSets(rules), ctx);

        assertEquals("One violation expected!", 1, report.size());

        RuleViolation rv = (RuleViolation) report.iterator().next();
        assertEquals(1, rv.getBeginLine());
    }

    private static final String MATCH
            = "<html><hr/></html>";

    private static final String XPATH_EXPRESSION
            = "//Element [@Name='hr']";

    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(XPathJspRuleTest.class);
    }
}
