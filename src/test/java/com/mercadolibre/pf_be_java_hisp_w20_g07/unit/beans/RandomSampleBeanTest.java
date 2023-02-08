package com.mercadolibre.pf_be_java_hisp_w20_g07.unit.beans;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mercadolibre.pf_be_java_hisp_w20_g07.beans.RandomSampleBean;
import com.mercadolibre.pf_be_java_hisp_w20_g07.dtos.SampleDTO;
import org.junit.jupiter.api.Test;

class RandomSampleBeanTest {

  @Test
  void randomPositiveTestOK() {
    RandomSampleBean randomSample = new RandomSampleBean();

    SampleDTO sample = randomSample.random();

    assertTrue(sample.getRandom() >= 0);
  }
}
