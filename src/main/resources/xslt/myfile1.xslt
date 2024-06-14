<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:ship="urn:shiporder"  exclude-result-prefixes="ship">  <xsl:template match="/">
    <h2>Ship Order Details</h2>
    <p><b>Order ID:</b> <xsl:value-of select="@orderid"/></p>

    <xsl:apply-templates select="*"/>
  </xsl:template>

  <xsl:template match="ship:orderperson">
    <p><b>Ordered by:</b> <xsl:value-of select="."/></p>
  </xsl:template>

  <xsl:template match="ship:shipto">
    <h3>Ship To:</h3>
    <p><xsl:value-of select="name"/></p>
    <p><xsl:value-of select="address"/></p>
    <p><xsl:value-of select="city"/>, <xsl:value-of select="country"/></p>
  </xsl:template>

  <xsl:template match="ship:item">
    <h4>Item:</h4>
    <p><b>Title:</b> <xsl:value-of select="title"/></p>
    <xsl:if test="note"> <p><b>Note:</b> <xsl:value-of select="note"/></p>
    </xsl:if>
    <p><b>Quantity:</b> <xsl:value-of select="quantity"/></p>
    <p><b>Price:</b> <xsl:value-of select="format-number(price, '#,##0.00')"/></p> </xsl:template>

</xsl:stylesheet>
