package ru.sadv1r.idea.plugin.ansible.vault;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Stream;

public abstract class LightAnsibleVaultCodeInsightTestCase extends LightPlatformCodeInsightFixtureTestCase {

    protected abstract String getDataPath();

    protected abstract String intentionName();

    public void doAllTests() {
        getTestFiles().forEach(e -> doTestFor(e, intentionName()));
    }

    public void doTestFor(String fileName, String intentionName) {
        myFixture.configureByFile(fileName);
        IntentionAction modifyVaultIntention = myFixture.findSingleIntention(intentionName);
        assertNotNull(modifyVaultIntention);
    }

    private Stream<String> getTestFiles() {
        final String testDirPath = getTestDataPath().replace(File.separatorChar, '/');
        File testDir = new File(testDirPath);
        final File[] files = testDir.listFiles();

        if (files == null || files.length == 0) {
            fail("Test files not found in " + testDirPath);
        }

        return Arrays.stream(files).map(File::getName);
    }

    @Override
    protected String getTestDataPath() {
        return "src/test/resources/intention/" + getDataPath();
    }

}
