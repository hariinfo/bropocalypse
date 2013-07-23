package net.nike.domain.product;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/** Created with IntelliJ IDEA. User: dboyd2 Date: 7/9/12 Time: 3:38 PM To change this template use File | Settings | File Templates. */
public class ProductSizeRun
{
  private Set<ProductSize> sizes = new LinkedHashSet<ProductSize>();

  public ProductSizeRun() {}

  public ProductSizeRun(Collection<ProductSize> sizes)
  {
    this.sizes.addAll(sizes);
  }

  public void addSize(ProductSize size)
  {
    sizes.add(size);
  }

  public void addSizes(Collection<ProductSize> sizes)
  {
    this.sizes.addAll(sizes);
  }

  public Collection<ProductSize> getSizes()
  {
    return Collections.unmodifiableCollection(sizes);
  }
}
