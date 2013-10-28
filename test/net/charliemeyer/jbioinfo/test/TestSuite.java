package net.charliemeyer.jbioinfo.test;

import net.charliemeyer.jbioinfo.test.lcs.LCSTests;
import net.charliemeyer.jbioinfo.test.profilematrix.ProfileMatrixTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({LCSTests.class, ProfileMatrixTests.class})
public class TestSuite {

}
