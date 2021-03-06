/*
 * Created on Jun 7, 2007
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2007-2011 the original author or authors.
 */
package org.fest.assertions;

import static org.fest.assertions.ErrorMessages.*;
import static org.fest.assertions.Formatting.format;
import static org.fest.assertions.Threshold.threshold;
import static org.fest.util.Objects.areEqual;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Assertions for <code>{@link BufferedImage}</code>s.
 * <p>
 * To create a new instance of this class invoke <code>{@link Assertions#assertThat(BufferedImage)}</code>.
 * </p>
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 * @author Ansgar Konermann
 */
public class ImageAssert extends GenericAssert<ImageAssert, BufferedImage> {

  private static final Threshold ZERO_THRESHOLD = threshold(0);

  private static ImageReader imageReader = new ImageReader();

  /**
   * Reads the image in the specified path.
   * @param imageFilePath the path of the image to read.
   * @return the read image.
   * @throws NullPointerException if the given path is {@code null}.
   * @throws IllegalArgumentException if the given path does not belong to a file.
   * @throws IOException if any I/O error occurred while reading the image.
   */
  public static BufferedImage read(String imageFilePath) throws IOException {
    if (imageFilePath == null) throw new NullPointerException("The path of the image to read should not be null");
    File imageFile = new File(imageFilePath);
    if (!imageFile.isFile())
      throw new IllegalArgumentException(format("The path <%s> does not belong to a file", imageFilePath));
    return imageReader.read(imageFile);
  }

  /**
   * Creates a new </code>{@link ImageAssert}</code>.
   * @param actual the target to verify.
   */
  protected ImageAssert(BufferedImage actual) {
    super(ImageAssert.class, actual);
  }

  /**
   * Verifies that the actual image is equal to the given one. Two images are equal if they have the same size and the
   * pixels at the same coordinates have the same color.
   * @param expected the given image to compare the actual image to.
   * @return this assertion object.
   * @throws AssertionError if the actual image is not equal to the given one.
   */
  @Override public ImageAssert isEqualTo(BufferedImage expected) {
    return isEqualTo(expected, ZERO_THRESHOLD);
  }

  /**
   * Verifies that the actual image is equal to the given one. Two images are equal if:
   * <ol>
   * <li>they have the same size</li>
   * <li>the difference between the RGB values of the color of each pixel is less than or equal to the given
   * threshold</li>
   * </ol>
   * @param expected the given image to compare the actual image to.
   * @param threshold the threshold to use to decide if the color of two pixels are similar: two pixels that are
   * identical to the human eye may still have slightly different color values. For example, by using a threshold of 1
   * we can indicate that a blue value of 60 is similar to a blue value of 61.
   * @return this assertion object.
   * @throws AssertionError if the actual image is not equal to the given one.
   * @since 1.1
   */
  public ImageAssert isEqualTo(BufferedImage expected, Threshold threshold) {
    if (areEqual(actual, expected)) return this;
    failIfNull(expected);
    failIfNotEqual(sizeOf(actual), sizeOf(expected));
    failIfNotEqualColor(expected, threshold);
    return this;
  }

  private void failIfNull(BufferedImage expected) {
    if (expected != null) return;
    failIfCustomMessageIsSet();
    fail(unexpectedNotEqual(actual, null));
  }

  private void failIfNotEqual(Dimension a, Dimension e) {
    if (areEqual(a, e)) return;
    failIfCustomMessageIsSet();
    fail(format("image size: expected:<%s> but was:<%s>", e, a));
  }

  private void failIfNotEqualColor(BufferedImage expected, Threshold threshold) {
    int w = actual.getWidth();
    int h = actual.getHeight();
    for (int x = 0; x < w; x++)
      for (int y = 0; y < h; y++)
        failIfNotEqual(new RGBColor(actual.getRGB(x, y)), new RGBColor(expected.getRGB(x, y)), threshold, x, y);
  }

  private void failIfNotEqual(RGBColor a, RGBColor e, Threshold threshold, int x, int y) {
    if (a.isEqualTo(e, threshold.value())) return;
    failIfCustomMessageIsSet();
    fail(String.format("expected:<%s> but was:<%s> at pixel [%d,%d]", a, e, x, y));
  }

  /**
   * Verifies that the actual image is not equal to the given one. Two images are equal if they have the same size and
   * the pixels at the same coordinates have the same color.
   * @param image the given image to compare the actual image to.
   * @return this assertion object.
   * @throws AssertionError if the actual image is equal to the given one.
   */
  @Override public ImageAssert isNotEqualTo(BufferedImage image) {
    if (areEqual(actual, image)) {
      failIfCustomMessageIsSet();
      throw failure(unexpectedEqual(actual, image));
    }
    if (image == null) return this;
    if (areEqual(sizeOf(actual), sizeOf(image)) && hasEqualColor(image)) {
      failIfCustomMessageIsSet();
      throw failure("images are equal");
    }
    return this;
  }

  private static Dimension sizeOf(BufferedImage image) {
    return new Dimension(image.getWidth(), image.getHeight());
  }

  private boolean hasEqualColor(BufferedImage expected) {
    int w = actual.getWidth();
    int h = actual.getHeight();
    for (int x = 0; x < w; x++)
      for (int y = 0; y < h; y++)
        if (actual.getRGB(x, y) != expected.getRGB(x, y)) return false;
    return true;
  }

  /**
   * Verifies that the size of the actual image is equal to the given one.
   * @param expected the expected size of the actual image.
   * @return this assertion object.
   * @throws AssertionError if the actual image is {@code null}.
   * @throws NullPointerException if the given size is {@code null}.
   * @throws AssertionError if the size of the actual image is not equal to the given one.
   */
  public ImageAssert hasSize(Dimension expected) {
    isNotNull();
    if (expected == null)
      throw new NullPointerException(formattedErrorMessage("The size to compare to should not be null"));
    Dimension actualDimension = new Dimension(actual.getWidth(), actual.getHeight());
    Fail.failIfNotEqual(customErrorMessage(), rawDescription(), actualDimension, expected);
    return this;
  }

  static void imageReader(ImageReader newImageReader) {
    imageReader = newImageReader;
  }
}
