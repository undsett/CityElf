package com.cityelf.utils.address.finder.utils;

import static org.assertj.core.api.Java6Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MaskCreatorTest {

  @Autowired
  private MaskCreator maskCreator;

  @Test
  public void getMaskWords1() throws Exception {
    String input = "космонавтов мал";
    List<String> maskWords = maskCreator.getMaskWords(input);
    assertThat(maskWords).containsExactly("%космонавтов%");
  }

  @Test
  public void getMaskWords2() throws Exception {
    String input = "терешковой валентины-мал";
    List<String> maskWords = maskCreator.getMaskWords(input);
    assertThat(maskWords).containsExactly("%терешковой%", "%валентины%");
  }

  @Test
  public void getMaskWords3() throws Exception {
    String input = "академика филатова  -малиновский";
    List<String> maskWords = maskCreator.getMaskWords(input);
    assertThat(maskWords).containsExactly("%академика%", "%филатова%");
  }

  @Test
  public void getMaskWords4() throws Exception {
    String input = "25-й чапаевской дивизии - мал";
    List<String> maskWords = maskCreator.getMaskWords(input);
    assertThat(maskWords).containsExactly("%чапаевской%", "%дивизии%");
  }

  @Test
  public void getMaskWords5() throws Exception {
    String input = "франко ул.";
    List<String> maskWords = maskCreator.getMaskWords(input);
    assertThat(maskWords).containsExactly("%франко%");
  }
}